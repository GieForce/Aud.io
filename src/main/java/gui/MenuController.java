package gui;

import aud.io.User;
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

    public void CreateParty(ActionEvent actionEvent) throws IOException {
        //Redirect to CreateParty
        CreatePartyView createParty = new CreatePartyView();
        createParty.start(stage, user);
    }

    public void JoinParty(ActionEvent actionEvent) throws IOException {
        //Redirect to JoinParty
        JoinPartyView joinParty = new JoinPartyView();
        joinParty.start(stage, user);
    }
}
