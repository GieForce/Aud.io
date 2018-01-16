package gui.controllers;

import aud.io.mongo.MongoDatabase;
import aud.io.rmi.ClientManager;
import aud.io.RegisteredUser;
import gui.views.MenuView;
import gui.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class RegisterController {
    @FXML
    private TextField tbMail;
    @FXML
    private TextField tbUsername;
    @FXML
    private PasswordField tbPassword;
    @FXML
    private PasswordField tbPasswordConfirm;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void register(ActionEvent actionEvent) throws Exception {
        String mail = tbMail.getText();
        String username = tbUsername.getText();
        String password = tbPassword.getText();
        String passwordConfirm = tbPasswordConfirm.getText();

        if (Objects.equals(mail, "") || mail == null && Objects.equals(username, "") || username == null ||
                Objects.equals(password, "") || password == null) {
            Message.Show("Error", "Please fill in all fields!");
        } else if (password.equals(passwordConfirm)) {
            ClientManager manager = RmiClient.getManager();
            MongoDatabase db = new MongoDatabase();

            RegisteredUser user = new RegisteredUser(mail, password, username);
            db.createUser(mail,username,password);
            RmiClient.getManager().login(username, password);

            //Show Menu
            MenuView menu = new MenuView();
            menu.start(stage, user);
        } else {
            Message.Show("Error", "Passwords don't match!");
        }
    }
}
