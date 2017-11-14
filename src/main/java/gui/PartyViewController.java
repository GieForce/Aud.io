package gui;

import aud.io.TemporaryUser;
import aud.io.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import server.Main;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PartyViewController {
    public TextArea taChat;
    public TextField tbMessage;
    public Label lbPartyName;

    private String name;
    private Stage stage;
    private String key;
    private User user;

    private static final Logger LOGGER = Logger.getLogger("Chat");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPartyInfo() {
        lbPartyName.setText(name);
        printSystemMessage("Party key is " + key);
    }

    public void SendMessage(ActionEvent actionEvent) {
        printUserMesage();
    }

    private void printUserMesage() {
        String message = tbMessage.getText();
        tbMessage.setText("");
        if(message != null && !Objects.equals(message, "")) {
            print(message, user);
        }
    }

    private void printSystemMessage(String msg) {
        print(msg, new TemporaryUser("System"));
    }

    private void print(String msg, User user) {
        String prev = taChat.getText();
        LOGGER.log(Level.INFO, String.format("[%s]: %s", user, msg));
        if(prev == null || Objects.equals(prev, "")) {
            taChat.setText(String.format("%s: %s", user.getNickname(), msg));
        } else {
            taChat.setText(String.format("%s\n%s: %s", prev, user.getNickname(), msg));
        }
    }

    public void EnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            printUserMesage();
        }
    }

    public void AddSongs(ActionEvent actionEvent) throws IOException {
        SongListView songListView = new SongListView();
        Stage s = new Stage();
        songListView.start(s);
    }

    public void VoteUp(ActionEvent actionEvent) {
        //TODO: Get song and add vote
    }

    public void VoteDown(ActionEvent actionEvent) {
        //TODO: Get song and add vote
    }

    public void VoteSkip(ActionEvent actionEvent) {
        //TODO: Get song and add vote
    }
}
