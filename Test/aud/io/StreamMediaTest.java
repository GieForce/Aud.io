package aud.io;

import aud.io.mongo.StreamMedia;
import org.junit.Test;

public class StreamMediaTest {

    @Test
    public void play() throws Exception {
        StreamMedia streamMedia = new StreamMedia("hier");
        streamMedia.play();
    }

}