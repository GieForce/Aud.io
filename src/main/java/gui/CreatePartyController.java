package gui;

import aud.io.Party;
import aud.io.User;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreatePartyController {
    public TextField tbPartyName;
    private Stage stage;
    private User user;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void CreateParty(ActionEvent actionEvent) throws IOException {
        String partyName = tbPartyName.getText();
        Message.Show("Info", partyName);

        PartyView partyView = new PartyView();
        partyView.start(stage, user);
    }
}
