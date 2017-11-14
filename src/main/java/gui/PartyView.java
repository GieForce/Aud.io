package gui;

import aud.io.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PartyView extends Application {
    private User user;
    private PartyViewController controller;
    private String name;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/party.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Party");
        controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(Stage stage, String name, User user) throws IOException {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/party.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Party");
        controller = loader.getController();
        controller.setStage(stage);
        controller.setUser(user);
        controller.setName(name);
        controller.setPartyInfo();
        stage.setScene(scene);
    }

    public void start(Stage stage, String name, String partyKey, User user) throws IOException {
        //TODO: do something with this user being the host
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/party.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Party");
        controller = loader.getController();
        controller.setStage(stage);
        controller.setUser(user);
        controller.setName(name);
        controller.setKey(partyKey);
        controller.setPartyInfo();
        stage.setScene(scene);
    }
}
