package gui;

import aud.io.RegisteredUser;
import aud.io.TemporaryUser;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button btnJoin;
    public ImageView imageLogo;
    public PasswordField tbPassword;
    public TextField tbUsername;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void Login(ActionEvent actionEvent) throws IOException {
        String user = tbUsername.getText();
        String password = tbPassword.getText();
        //TODO: Check details in database
        if (true) {
            //TODO: Change this to actual registered user
            RegisteredUser regUser = new RegisteredUser("real@e.mail", user, password);
            MenuView menu = new MenuView();
            menu.start(stage, regUser);
        } else {
            Message.Show("Error", "Login details don't match!");
        }
    }

    public void GuestLogin(MouseEvent mouseEvent) throws IOException {
        //Redirect to party list
        TempUserView tempUserView = new TempUserView();
        tempUserView.start(stage, new TemporaryUser("Unset"));
    }

    public void SignUp(MouseEvent mouseEvent) throws IOException {
        //Redirect to Register
        RegisterView register = new RegisterView();
        register.start(stage);
    }
}
