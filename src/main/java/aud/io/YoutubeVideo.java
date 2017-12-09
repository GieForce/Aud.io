package aud.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.Serializable;

/**
 * @deprecated
 */
public class YoutubeVideo extends Votable implements Serializable {

    //MongoDB van Track
    private static final String ARTIST = "Artist";

    //MongoDB van Votable
    private static final String MEDIA = "Media";
    private static final String NAME = "Name";
    private static final String LENGTH = "Length";

    private String artist;

    @MongoObjectId
    private String _id;

    @JsonCreator
    public YoutubeVideo(@JsonProperty(MEDIA) IMedia media,
                        @JsonProperty(NAME) String name,
                        @JsonProperty(LENGTH) float length,
                        @JsonProperty(ARTIST) String artist) {
        super(media, name, length);
        this.artist = artist;
    }
}
