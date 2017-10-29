package aud.io;

import org.junit.Test;

import static org.junit.Assert.*;

public class StreamMediaTest {

    @Test
    public void play() throws Exception {
        StreamMedia streamMedia = new StreamMedia("hier");
        streamMedia.play();
    }

}