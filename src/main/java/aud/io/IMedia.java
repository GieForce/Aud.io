package aud.io;

import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS,property="_class")
public interface IMedia {
    void play();
}
