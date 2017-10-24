package gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class JoinController {
    public ImageView imageBackground;
    public VBox vboxRoot;
    public Button btnJoin;
    public TextField tbPartyId;
    public ImageView imageLogo;
    public HBox hboxLabel;

    public void JoinParty(ActionEvent actionEvent) {
        String partyId = tbPartyId.getText();
        if (partyId == null || Objects.equals(partyId, "")) {
            Message.Show("Error", "Please fill in Party ID");
        } else {
            Message.Show("Info", partyId);
        }
    }
}
