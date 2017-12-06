package aud.io.mongo;

import aud.io.IMedia;
import aud.io.drive.DriveManager;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.*;
import java.util.concurrent.Callable;

public class StreamMedia implements IMedia, Serializable {

    private static final String STREAM_MEDIA = "StreamMedia";

    private transient DriveManager driveManager;

    private String streamLocation;

    @MongoObjectId
    private String _id;

    @JsonCreator
    public StreamMedia(@JsonProperty(STREAM_MEDIA) String streamLocation) {
        this.streamLocation = streamLocation;
    }

    @Override
    public void play() {

    }

    @Override
    public Callable<File> getFile() {
        return () -> {
            driveManager = new DriveManager();
            return new File(driveManager.download(streamLocation));
        };
    }

    @Override
    public String toString() {
        return streamLocation;
    }
}
