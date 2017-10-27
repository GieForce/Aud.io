package aud.io;

import java.io.Serializable;
import java.util.Properties;

public class MemoryMedia implements IMedia, Serializable {

    private String name;

    public MemoryMedia(String name) {
        this.name = name;
    }


    @Override
    public void play() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("Now playing: %s%n", name);
            }
        })).start();
    }
}
