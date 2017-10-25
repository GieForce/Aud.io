package gui;

import aud.io.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JoinPartyController {
    public Button btnJoin;
    public TextField tbPartyId;
    private Stage stage;
    private User user;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void JoinParty(ActionEvent actionEvent) throws IOException {
        String partyId = tbPartyId.getText();
        if (partyId == null || Objects.equals(partyId, "")) {
            Message.Show("Error", "Please fill in Party ID");
        } else {
            Message.Show("Info", partyId);
            //TODO: Somehow find party by Id
            PartyView partyView = new PartyView();
            partyView.start(stage, user);
        }
    }

}
