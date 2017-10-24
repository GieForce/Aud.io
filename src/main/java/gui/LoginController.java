package gui;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoginController {
    public Button btnJoin;
    public ImageView imageLogo;
    public PasswordField tbPassword;
    public TextField tbUsername;


    public void Login(ActionEvent actionEvent) {
        String user = tbUsername.getText();
        String password = tbPassword.getText();
        //TODO: LoginView
        Message.Show("LoginView details", user + " " + password);
    }

    public void GuestLogin(MouseEvent mouseEvent) {
        //Redirect to party list
        Message.Show("Clicked", "Clicked");
    }

    public void SignUp(MouseEvent mouseEvent) {
        //Redirect to Sign Up.
        Message.Show("Clicked", "Clicked");
    }
}
