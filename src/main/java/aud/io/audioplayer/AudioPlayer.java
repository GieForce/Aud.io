package aud.io.audioplayer;

import aud.io.IMedia;
import aud.io.IPlayer;
import aud.io.Votable;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class AudioPlayer extends Observable implements IPlayer {
    private final ExecutorService pool;
    private  AudioMediaPlayerComponent VLCPlayer;
    private Future currentSong;

    public AudioPlayer(ExecutorService pool, AudioMediaPlayerComponent VLCPlayer) {
        this.pool = pool;

        //TODO: Insert actual player
        this.VLCPlayer = VLCPlayer;
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
        currentSong.cancel(true);
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
        private AudioMediaPlayerComponent VLCPlayer;
        private AtomicBoolean play, pause, stop;
        private boolean loadedSong = false;

        PlayerRunnable(IMedia trackMedia, ExecutorService pool, AudioPlayer player, AudioMediaPlayerComponent VLCPlayer) {
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
                e.printStackTrace();
            }

            while (!Thread.currentThread().isInterrupted()){
                if (play.get()){
                    play.set(false);
                    if (!loadedSong){
                        //TODO: load song
                        loadedSong = true;
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
