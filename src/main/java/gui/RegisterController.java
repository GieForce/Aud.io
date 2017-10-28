package gui;

import aud.io.ClientManager;
import aud.io.RegisteredUser;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class RegisterController {
    public TextField tbMail;
    public TextField tbUsername;
    public PasswordField tbPassword;
    public PasswordField tbPasswordConfirm;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void Register(ActionEvent actionEvent) throws Exception {
        String mail = tbMail.getText();
        String username = tbUsername.getText();
        String password = tbPassword.getText();
        String passwordConfirm = tbPasswordConfirm.getText();

        if (Objects.equals(mail, "") || mail == null && Objects.equals(username, "") || username == null ||
                Objects.equals(password, "") || password == null) {
            Message.Show("Error", "Please fill in all fields!");
        } else if (password.equals(passwordConfirm)) {
            ClientManager manager = RmiClient.getManager();
            //TODO: Implement manager register
            RegisteredUser user = new RegisteredUser(mail, username, password);
            //Show Menu
            MenuView menu = new MenuView();
            menu.start(stage, user);
        } else {
            Message.Show("Error", "Passwords don't match!");
        }
    }
}
