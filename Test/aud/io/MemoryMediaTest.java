package aud.io;

import aud.io.memory.MemoryMedia;
import org.junit.Test;

public class MemoryMediaTest {
    @Test
    public void play() throws Exception {
        MemoryMedia memoryMedia = new MemoryMedia("Test");
        memoryMedia.play();
    }

}