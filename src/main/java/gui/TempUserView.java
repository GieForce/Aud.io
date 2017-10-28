package gui;

import aud.io.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TempUserView {
    private TempUserController controller;

//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/temp.fxml"));
//        Parent parent = loader.load();
//        primaryStage.setTitle("Temporary User");
//        controller = loader.getController();
//        controller.setStage(primaryStage);
//        Scene scene = new Scene(parent);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/temp.fxml"));
        Parent parent = loader.load();
        stage.setTitle("Temporary User");
        controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
}
