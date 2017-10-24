package gui;

import aud.io.Party;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class CreatePartyController {
    public TextField tbPartyName;

    public void CreateParty(ActionEvent actionEvent) {
        String partyName = tbPartyName.getText();
        Message.Show("Info", partyName);
    }
}
