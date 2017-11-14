package aud.io.memory;

import aud.io.IMedia;

import java.io.File;
import java.io.Serializable;
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

    private class GetFileCallable implements Callable<File>{

        private String fileLocation;

        GetFileCallable(String fileLocation) {
            this.fileLocation = fileLocation;
        }

        @Override
        public File call() throws Exception {

            return new File(fileLocation);
        }
    }
}
