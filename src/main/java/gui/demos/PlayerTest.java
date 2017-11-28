package gui.demos;

import aud.io.IPlayer;
import aud.io.Votable;
import aud.io.audioplayer.AudioPlayer;
import aud.io.audioplayer.Track;
import aud.io.memory.MemoryMedia;
import com.sun.jna.Native;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerTest {

    private static IPlayer player;
    private  static ExecutorService pool = Executors.newFixedThreadPool(3);

    public static void main(String[] args){

        //System.load("C:\\Users\\Nick Hammes\\IdeaProjects\\Aud.io\\src\\main\\resources\\lib\\libvlc.dll");

        Scanner scanner = new Scanner(System.in);

        boolean found = new NativeDiscovery().discover();
        System.out.println(found);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());

        AudioMediaPlayerComponent VLCplayer = new AudioMediaPlayerComponent();

        player = new AudioPlayer(pool, VLCplayer);

        Votable votable = new Track(new MemoryMedia("C:\\Programming\\Java\\Aud.io\\src\\main\\resources\\audio\\Demo.mp3"));

        player.play(votable);

        boolean loop = true;
        while (loop){
            if (executeNextLine(scanner.nextLine())){
                loop = false;
                System.out.println("Bye bye");
                pool.shutdown();
                System.exit(0);
            }
        }

    }

    private static boolean executeNextLine(String s) {
        if (s.equals("new")){
            player.play(new Track(new MemoryMedia("C:\\Programming\\Java\\Aud.io\\src\\main\\resources\\audio\\Demo.mp3")));
            return false;
        }
        if (s.equals("play")){
            player.play();
            return false;
        }
        if (s.equals("pause")){
            player.pause();
            return false;
        }
        if (s.equals("stop")){
            player.stop();
            return false;
        }
        if (s.equals("quit")){
            return true;
        }
        return false;
    }
}
