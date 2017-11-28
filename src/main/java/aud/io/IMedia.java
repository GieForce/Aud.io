package aud.io;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.concurrent.Callable;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS,property="_class")
public interface IMedia {
    /**
     * @deprecated
     */
    void play();

    Callable getFile();
}
