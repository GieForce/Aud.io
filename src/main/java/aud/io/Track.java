package aud.io;

public class Track extends Votable {
    private String artist;
    private String album;

    /**
     * Create new Track using Media
     * @param media Media where the Track is at
     */
    public Track(IMedia media) {
        super(media);
    }

    /**
     * /**
     * Create new Track
     * @param media Media where the Track is at
     * @param name Name of the Track
     * @param length Length of the Track
     * @param artist Artist of the Track
     * @param album Album of the Track
     */
    public Track(IMedia media, String name, float length, String artist, String album) {
        super(media, name, length);
        this.artist = artist;
        this.album = album;
    }

    /**
     * Get Artist
     * @return artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Get Album
     * @return album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Set Album
     * @param value new Album value
     */
    public void setAlbum(String value) {
        album = value;
    }
}
