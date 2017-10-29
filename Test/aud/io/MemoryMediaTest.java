package aud.io;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryMediaTest {
    @Test
    public void play() throws Exception {
        MemoryMedia memoryMedia = new MemoryMedia("Test");
        memoryMedia.play();
    }

}