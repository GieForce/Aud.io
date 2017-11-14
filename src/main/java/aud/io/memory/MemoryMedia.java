package aud.io.memory;

import aud.io.IMedia;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class MemoryMedia implements IMedia, Serializable {

    private String name;

    public MemoryMedia(String name) {
        this.name = name;
    }


    @Override
    public void play() {
        (new Thread(() -> System.out.printf("Now playing: %s%n", name))).start();
    }

    @Override
    public Callable getFile() {
        return null;
    }
}
