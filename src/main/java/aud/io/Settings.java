package aud.io;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private ArrayList<String> blockedArtists;
    private ArrayList<String> blockedSongs;

    public Settings() {
        blockedArtists = new ArrayList<>();
        blockedSongs = new ArrayList<>();
    }

    public void blockArtist(String artist) {
        if (!blockedArtists.contains(artist)) blockedArtists.add(artist);
    }

    public void blockSong(String song) {
        if (!blockedSongs.contains(song)) blockedSongs.add(song);
    }

    public void unblockArtist(String artist) {
        if (blockedArtists.contains(artist)) blockedArtists.remove(artist);
    }

    public void unblockSong(String song) {
        if (blockedSongs.contains(song)) blockedSongs.remove(song);
    }

    public List<String> getBlockedArtists() {
        return blockedArtists;
    }

    public List<String> getBlockedSongs() {
        return blockedSongs;
    }
}
