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
import log.Logger;
import rmi.ApplicationServer;

import java.io.IOException;
import java.util.Objects;

public class CreatePartyController {
    @FXML
    private TextField tbPartyName;
    private Stage stage;
    private User user;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createParty(ActionEvent actionEvent) throws IOException {
        Logger.setupLogger(CreatePartyController.class.getName());
        String partyName = tbPartyName.getText();
        if (Objects.equals(partyName, "") || partyName == null) {
            Message.Show("Error", "Please enter a partyname");
        } else {
            ClientManager manager = RmiClient.getManager();
            manager.createParty(partyName);
            Party p = manager.getParty();
            p.getPartyKey();
            p.getName();
            PartyView partyView = new PartyView();
            partyView.start(stage, p.getName(), p.getPartyKey(), user);
        }
        Message.Show("Info", partyName);
    }
}
