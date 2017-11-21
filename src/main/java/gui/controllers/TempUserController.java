package gui.controllers;

import aud.io.rmi.ClientManager;
import aud.io.TemporaryUser;
import gui.views.JoinPartyView;
import gui.Message;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TempUserController {
    public TextField tbTempName;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void SetName(ActionEvent actionEvent) throws IOException {
        String nickname = tbTempName.getText();
        if (!Objects.equals(nickname, "") || nickname != null) {
            ClientManager manager = RmiClient.getManager();
            manager.getTemporaryUser(nickname);
            TemporaryUser user = (TemporaryUser) manager.getUser();
            JoinPartyView joinParty = new JoinPartyView();
            joinParty.start(stage, user);
        } else {
            Message.Show("Error", "Please enter a nickname");
        }
    }
}
