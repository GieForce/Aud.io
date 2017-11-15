package gui.controllers;

import aud.io.User;
import gui.views.CreatePartyView;
import gui.views.JoinPartyView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private User user;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createParty(ActionEvent actionEvent) throws IOException {
        //Redirect to createParty
        CreatePartyView createParty = new CreatePartyView();
        createParty.start(stage, user);
    }

    public void joinParty(ActionEvent actionEvent) throws IOException {
        //Redirect to joinParty
        JoinPartyView joinParty = new JoinPartyView();
        joinParty.start(stage, user);
    }
}
