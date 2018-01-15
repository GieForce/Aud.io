package gui.views;

import gui.controllers.SettingsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsView extends Application {
    private SettingsController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/settings.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Settings");
        controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setWidth(510);
        primaryStage.setHeight(500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
