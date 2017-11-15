package aud.io;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS,property="_class")
public interface IMedia {
    void play();
}
