package aud.io;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Votable votable = new Track(new StreamMedia("eenLocatie"), "slapend rijk", 0f, "boef", "slaaptekort" );
        MongoDatabase mongoDatabase = new MongoDatabase();
        mongoDatabase.saveVotable(votable);

        ArrayList<Votable> votableArrayList = mongoDatabase.getSongsWithSearchterm("");
        //System.out.println(votableArrayList.toString());
        for (Votable sVotable: votableArrayList
             ) {
            System.out.println(sVotable.getName());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
