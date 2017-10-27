package aud.io;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.Serializable;

public class StreamMedia implements IMedia, Serializable {

    private static final String STREAMMEDIA = "StreamMedia";

    private String streamLocation;

    @MongoObjectId
    private String _id;

    @JsonCreator
    public StreamMedia(@JsonProperty(STREAMMEDIA) String streamLocation)
    {
        this.streamLocation = streamLocation;
    }

    @Override
    public void play() {
        //TODO:Toevoegen afspelen van Streammedia

        //Praat met audioserver voor ophalen media

        //
    }
}
