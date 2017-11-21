package gui.controllers;

import aud.io.rmi.ClientManager;
import aud.io.RegisteredUser;
import gui.views.MenuView;
import gui.Message;
import gui.views.RegisterView;
import gui.views.TempUserView;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    public Button btnJoin;
    public ImageView imageLogo;
    public PasswordField tbPassword;
    public TextField tbUsername;

    private Stage stage;
    private ClientManager manager;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        new RmiClient().setupManager();
        manager = RmiClient.getManager();
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        String user = tbUsername.getText();
        String password = tbPassword.getText();
        //TODO: Check details in database
        if (!Objects.equals(user, "") || user != null || !Objects.equals(password, "") || password != null) {
            manager.login(user, password);
            RegisteredUser regUser = (RegisteredUser) manager.getUser();
            if (regUser != null) {
                MenuView menu = new MenuView();
                menu.start(stage, regUser);
            } else {
                Message.Show("Error", "Login details don't match!");
            }
        } else {
            Message.Show("Error", "One or more fields were empty, please fill those in.");
        }
    }

    public void GuestLogin(MouseEvent mouseEvent) throws IOException {
        //Redirect to party list
        TempUserView tempUserView = new TempUserView();
        tempUserView.start(stage);
    }

    public void SignUp(MouseEvent mouseEvent) throws IOException {
        //Redirect to Register
        RegisterView register = new RegisterView();
        register.start(stage);
    }
}
