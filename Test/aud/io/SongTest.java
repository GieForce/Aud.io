package aud.io;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SongTest {

    Song song;

    @Before
    public void setUp() {
        song = new Song
                (656, "Hurt", "Spotify", "Country", "American IX", "Johnny Cash");

    }

    @Test
    public void getLengte() {
        assertEquals(656, song.getLengte());
    }

    @Test
    public void getNaam() {
        assertEquals("Hurt", song.getNaam());
    }

    @Test
    public void getLocatie(){
        assertEquals("Spotify", song.getLocatie());
    }

    @Test
    public void getGenre() {
        assertEquals("Country", song.getGenre());
    }

    @Test
    public void getAlbum() {
        assertEquals("American IX", song.getAlbum());
    }

    @Test
    public void getArtist() {
        assertEquals("Johnny Cash", song.getArtist());
    }

}