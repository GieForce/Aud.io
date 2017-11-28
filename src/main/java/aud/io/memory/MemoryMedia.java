package aud.io.memory;

import aud.io.IMedia;
import com.google.api.services.drive.model.File;


import java.io.*;
import java.util.concurrent.Callable;

public class MemoryMedia implements IMedia, Serializable {

    private String location;

    public MemoryMedia(String location) {
        this.location = location;
    }


    @Override
    public void play() {

    }

    @Override
    public Callable<File> getFile() {
        return new GetFileCallable(location);
    }

    public void getFiles() throws IOException
    {

    }

    private class GetFileCallable implements Callable<File>{

        private String fileLocation;

        GetFileCallable(String fileLocation) {
            this.fileLocation = fileLocation;
        }

        @Override
        public File call() throws Exception {

            return new File();
        }
    }
}
