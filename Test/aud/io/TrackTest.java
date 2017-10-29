package aud.io;

import org.junit.Before;
import org.junit.Test;

import javax.print.attribute.standard.Media;

import static org.junit.Assert.*;

public class TrackTest {

    Track publictrack;

    @Before
    public void setUp() {
        StreamMedia mediastream = new StreamMedia("spotify");
        publictrack = new Track(mediastream, "Solitary Man", 4.1f, "Johny Cash", "American IX");
    }

    @Test
    public void getArtist() {
        assertEquals("Johny Cash", publictrack.getArtist());
    }

    @Test
    public void getAlbum() {
        assertEquals("American IX", publictrack.getAlbum());

    }

    @Test
    public void setAlbum() {
        String newalbum = "American Made";
        publictrack.setAlbum(newalbum);
        assertEquals("American Made", publictrack.getAlbum());
        publictrack.setAlbum("American IX");
    }

}