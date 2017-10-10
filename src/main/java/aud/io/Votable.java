package aud.io;

public class Votable {
    private String name;
    private float lenght;
    IMedia media;

    public Votable(String name, float lenght, IMedia media) {
        this.name = name;
        this.lenght = lenght;
        this.media = media;
    }

    public String getName() {
        return name;
    }

    public float getLenght() {
        return lenght;
    }

    public IMedia getMedia() {
        return media;
    }


}
