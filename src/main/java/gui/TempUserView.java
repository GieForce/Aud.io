package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TempUserView {
    private TempUserController controller;

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/temp.fxml"));
        Parent parent = loader.load();
        stage.setTitle("Temporary User");
        controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
}
