package aud.io.audioplayer;

import aud.io.IMedia;
import aud.io.IPlayer;
import aud.io.Votable;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AudioPlayer implements IPlayer {
    private final ExecutorService pool;

    public AudioPlayer(ExecutorService pool) {
        this.pool = pool;
    }


    @Override
    public void play() {
        //resume playing previously loaded song
    }

    @Override
    public void stop() {
        //stop playing and empty loaded song
    }

    @Override
    public void pause() {
        //pause song
    }

    @Override
    public void play(Votable votable) {
        //load new song and start playing
        pool.submit(new PlayerRunnable(votable.getMedia(), pool));
    }

    private class PlayerRunnable implements Runnable{

        private Future mediaFileContainer;
        private File mediaFile;
        private IMedia trackMedia;
        private ExecutorService pool;

        PlayerRunnable(IMedia trackMedia, ExecutorService pool) {
            this.trackMedia = trackMedia;
            this.pool = pool;
        }

        @Override
        public void run() {
            mediaFileContainer = pool.submit(trackMedia.getFile());

            try {
                mediaFile = (File)mediaFileContainer.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            playFile(mediaFile);

        }

        private  void playFile(File mediaFile){
            System.out.println(mediaFile);
        }
    }
}
