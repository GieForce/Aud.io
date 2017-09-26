package aud.io;

public class Song {
    private float lengte;
    private String naam;
    private String locatie;
    private String genre;
    private String album;
    private String artist;

    public Song(float lengte, String naam, String locatie, String genre, String album, String artist) {
        this.lengte = lengte;
        this.naam = naam;
        this.locatie = locatie;
        this.genre = genre;
        this.album = album;
        this.artist = artist;
    }
}
