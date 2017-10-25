package gui;

import aud.io.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreatePartyView extends Application {
    private User user;
    private CreatePartyController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/create.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Create Party");
        controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(Stage stage, User user) throws IOException {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/create.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Create Party");
        controller = loader.getController();
        controller.setStage(stage);
        controller.setUser(user);
        stage.setScene(scene);
    }
}
