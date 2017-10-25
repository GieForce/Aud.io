package gui;

import aud.io.User;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class PartyViewController {
    public TextArea taChat;
    public TextField tbMessage;
    private Stage stage;
    private User user;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void SendMessage(ActionEvent actionEvent) {
        String message = tbMessage.getText();
        tbMessage.setText("");
        if(message != null && !Objects.equals(message, "")) {
            String prev = taChat.getText();
            if(prev == null || Objects.equals(prev, "")) {
                taChat.setText(String.format("%s: %s", user.toString(), message));
            } else {
                taChat.setText(String.format("%s\n%s: %s", prev, user.toString(), message));
            }
        }
    }
}
