package aud.io;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.AlbumRequest;
import com.wrapper.spotify.models.Album;
import net.sf.json.JSONObject;


import java.io.IOException;
import java.util.List;

public class SpotifyMedia implements IMedia {

    //Api api = Api.DEFAULT_API;



    Api api = Api.builder()
            .accessToken("<your_access_token>")
            .refreshToken("<your_refresh_token>")
            .build();

    // Create a request object for the type of request you want to make
    AlbumRequest request = api.getAlbum("7e0ij2fpWaxOEHv5fUYZjd").build();

// Retrieve an album
    public void getAlbumByName() {
        try {
            Album album = null;
            try {
                album = request.get();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (WebApiException e1) {
                e1.printStackTrace();
            }

            // Print the genres of the album
            List<String> genres = album.getGenres();
            for (String genre : genres) {
                System.out.println(genre);
            }
        } catch (Exception e) {
            System.out.println("Could not get albums.");
        }
    }

        // In case of failure

    public void onFailure(Throwable thrown) {
        System.out.println("Could not get albums.");
    }



    @Override
    public JSONObject getFile(String zoekterm) {
        return null;
    }

//    public void getClipByName()
//    {
//        URI uri = new URIBuilder()
//        .setScheme("http")
//        .setHost("api.spotify.com")
//        .setPath("v1")
//
//
//    }





    public void getClipByName()
    {

    }
}
