package gui.demos;

import aud.io.IPlayer;
import aud.io.Votable;
import aud.io.audioplayer.AudioPlayer;
import aud.io.audioplayer.Track;
import aud.io.drive.DriveManager;
import aud.io.memory.MemoryMedia;
import aud.io.mongo.MongoDatabase;
import com.sun.jna.Native;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerTest {

    private static IPlayer player;
    private static ExecutorService pool = Executors.newFixedThreadPool(3);
    private static String path = "src/main/resources/audio/Demo.mp3";

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        boolean found = new NativeDiscovery().discover();
        System.out.println(found);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());

        AudioMediaPlayerComponent vlcplayer = new AudioMediaPlayerComponent();

        player = new AudioPlayer(pool, vlcplayer);

        MongoDatabase mongoDatabase = new MongoDatabase();
        List<Votable> votableList = mongoDatabase.getAllSongs();

        player.play(votableList.get(4));


        boolean loop = true;
        while (loop) {
            if (executeNextLine(scanner.nextLine())) {
                loop = false;
                System.out.println("Bye bye");
                pool.shutdown();
                System.exit(0);
            }
        }

    }

    private static boolean executeNextLine(String s) {
        if (s.equals("new")) {
            player.play(new Track(new MemoryMedia(path)));
            return false;
        }
        if (s.equals("play")) {
            player.play();
            return false;
        }
        if (s.equals("pause")) {
            player.pause();
            return false;
        }
        if (s.equals("stop")) {
            player.stop();
            return false;
        }
        if (s.equals("quit")) {
            return true;
        }
        return false;
    }
}
