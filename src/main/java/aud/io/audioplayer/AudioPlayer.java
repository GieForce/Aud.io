package aud.io.audioplayer;

import aud.io.IMedia;
import aud.io.IPlayer;
import aud.io.Votable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.co.caprica.vlcj.player.MediaPlayer;

public class AudioPlayer extends Observable implements IPlayer {
    private final ExecutorService pool;
    private  MediaPlayer VLCPlayer;
    private Future currentSong;
    private Logger logger;

    public AudioPlayer(ExecutorService pool, MediaPlayer VLCPlayer) {
        this.pool = pool;
        setupLogger();
        //TODO: Insert actual player
        this.VLCPlayer = VLCPlayer;
    }

    private void setupLogger() {
        try {
            String logname = "AudioPlayer";
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
            FileHandler fh = new FileHandler(String.format("logs/%s-%s.log", logname, timeStamp));
            fh.setLevel(Level.ALL);
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.ALL);
            logger = java.util.logging.Logger.getLogger(logname);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void play() {
        //resume playing previously loaded song
        hasChanged();
        notifyObservers(PlayerOption.Play);
    }

    @Override
    public void stop() {
        //stop playing and empty loaded song
        hasChanged();
        notifyObservers(PlayerOption.Stop);
    }

    @Override
    public void pause() {
        //pause song
        hasChanged();
        notifyObservers(PlayerOption.Pause);
    }

    @Override
    public void play(Votable votable) {
        //load new song and start playing
        //TODO: Revise with thread handling in mind
        Future newSong = pool.submit(new PlayerRunnable(votable.getMedia(), pool, this, VLCPlayer));
        if (currentSong != null){
            currentSong.cancel(true);
        }
        currentSong = newSong;
        play();
    }

    private enum PlayerOption{
        Play,
        Pause,
        Stop
    }

    private class PlayerRunnable implements Runnable, Observer{

        private Future mediaFileContainer;
        private File mediaFile;
        private IMedia trackMedia;
        private ExecutorService pool;
        private MediaPlayer VLCPlayer;
        private AtomicBoolean play, pause, stop;
        private boolean loadedSong = false;

        PlayerRunnable(IMedia trackMedia, ExecutorService pool, AudioPlayer player, MediaPlayer VLCPlayer) {
            this.trackMedia = trackMedia;
            this.pool = pool;
            player.addObserver(this);
            this.VLCPlayer = VLCPlayer;
            play = new AtomicBoolean(false);
            pause = new AtomicBoolean(false);
            stop = new AtomicBoolean(false);
        }

        @Override
        public void run() {
            mediaFileContainer = pool.submit(trackMedia.getFile());

            try {
                mediaFile = (File)mediaFileContainer.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.log(Level.SEVERE, e.toString());
            }

            while (!Thread.currentThread().isInterrupted()){
                if (play.get()){
                    play.set(false);
                    if (!loadedSong){
                        //TODO: load song
                        VLCPlayer.playMedia("audio/Demo.mp3");
                        loadedSong = true;
                        VLCPlayer.play();
                    }
                    //TODO: play
                }
                if (pause.get()){
                    pause.set(false);
                    //TODO: pause
                }
                if (stop.get()){
                    stop.set(false);
                    //TODO: stop
                }
            }

            //TODO: stop song, handle thread finishing

        }

        private  void playFile(File mediaFile){
            System.out.println(mediaFile);
        }

        @Override
        public synchronized void update(Observable o, Object arg) {
            if (arg instanceof PlayerOption){
                switch ((PlayerOption)arg){
                    case Play:
                        play.set(true);
                        break;
                    case Pause:
                        pause.set(true);
                        break;
                    case Stop:
                        stop.set(true);
                        break;
                }
            }
        }
    }
}
