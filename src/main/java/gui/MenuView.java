package gui;

import aud.io.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuView extends Application {
    private User user;
    private MenuController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Parent parent = loader.load();
        primaryStage.setTitle("Menu");
        controller = loader.getController();
        controller.setStage(primaryStage);
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(Stage stage, User user) throws IOException {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Parent parent = loader.load();
        stage.setTitle("Menu");
        controller = loader.getController();
        controller.setStage(stage);
        controller.setUser(user);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
}
