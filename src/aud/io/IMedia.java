package aud.io;

import net.sf.json.JSONObject;

import java.util.Properties;

public interface IMedia {
    JSONObject getFile(String zoekterm);
    Properties getProperties();
}
