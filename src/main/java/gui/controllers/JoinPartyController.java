package gui.controllers;

import aud.io.rmi.ClientManager;
import aud.io.Party;
import aud.io.User;
import gui.Message;
import gui.views.PartyView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JoinPartyController {
    @FXML
    private TextField tbPartyId;
    private Stage stage;
    private User user;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void joinParty(ActionEvent actionEvent) throws IOException {
        String partyKey = tbPartyId.getText();
        if (partyKey == null || Objects.equals(partyKey, "")) {
            Message.Show("Error", "Please fill in Party ID");
        } else {
            ClientManager manager = RmiClient.getManager();
            manager.joinParty(partyKey);
            Party p = manager.getParty();
            if (p != null) {
                PartyView partyView = new PartyView();
                partyView.start(stage, p.getName(), user);
            } else {
                Message.Show("Error", "No party with key " + partyKey);
            }
        }
    }

}
