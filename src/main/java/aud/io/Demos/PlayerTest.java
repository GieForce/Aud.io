package aud.io.Demos;

import aud.io.Votable;
import aud.io.audioplayer.AudioPlayer;
import aud.io.audioplayer.Track;
import aud.io.memory.MemoryMedia;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerTest {

    private static AudioPlayer player;
    private  static ExecutorService pool = Executors.newFixedThreadPool(3);

    public static void main(String[] args){
        player = new AudioPlayer(pool);

        Votable votable = new Track(new MemoryMedia("audio/Demo.mp3"));

        player.play(votable);

        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            if (scanner.nextLine().equals("quit")){
                loop = false;
                System.out.println("Bye bye");
                pool.shutdown();
            }
        }

    }
}
