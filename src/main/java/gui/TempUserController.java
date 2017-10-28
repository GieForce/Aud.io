package gui;

import aud.io.User;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TempUserController {
    public TextField tbTempName;
    private Stage stage;
    private User user;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void SetName(ActionEvent actionEvent) throws IOException {
        String nickname = tbTempName.getText();
        if(!Objects.equals(nickname, "") || nickname != null) {
            user.setNickname(nickname);

            JoinPartyView joinParty = new JoinPartyView();
            joinParty.start(stage, user);
        }
        else {
            Message.Show("Error", "Please enter a nickname");
        }
    }
}
