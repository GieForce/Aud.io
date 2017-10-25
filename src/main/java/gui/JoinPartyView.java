package gui;

import aud.io.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinPartyView extends Application {
    private User user;
    private JoinPartyController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/join.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        primaryStage.setTitle("Join Party");
        controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(Stage stage, User user) throws IOException {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/join.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Join Party");
        controller = loader.getController();
        controller.setStage(stage);
        controller.setUser(user);
        stage.setScene(scene);
    }
}
