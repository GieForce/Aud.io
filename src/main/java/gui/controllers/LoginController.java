package gui.controllers;

import aud.io.mongo.Connection;
import aud.io.mongo.MongoDatabase;
import aud.io.rmi.ClientManager;
import aud.io.RegisteredUser;
import gui.views.RegisterView;
import gui.views.TempUserView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jongo.MongoCollection;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button btnJoin;
    @FXML
    private ImageView imageLogo;
    @FXML
    private PasswordField tbPassword;
    @FXML
    private TextField tbUsername;

    private Stage stage;
    private ClientManager manager;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        new RmiClient().setupManager();
        manager = RmiClient.getManager();
    }

    public void login(ActionEvent actionEvent) throws IOException {

        String username = tbUsername.getText();
        String password = tbPassword.getText();

        MongoDatabase md = new MongoDatabase();

        md.loginUser(username, password);



        /*String username = tbUsername.getText();
        String password = tbPassword.getText();
        //TODO: Check details in database
        if (!Objects.equals(user, "") || user != null || !Objects.equals(password, "") || password != null) {
            manager.login(user, password);
            RegisteredUser regUser = (RegisteredUser) manager.getUser();
            if (regUser != null) {
                MenuView menu = new MenuView();
                menu.start(stage, regUser);
            } else {
                Message.Show("Error", "login details don't match!");
            }
        } else {
            Message.Show("Error", "One or more fields were empty, please fill those in.");
        }*/
    }

    public void guestLogin(MouseEvent mouseEvent) throws IOException {
        //Redirect to party list
        TempUserView tempUserView = new TempUserView();
        tempUserView.start(stage);
    }

    public void signUp(MouseEvent mouseEvent) throws IOException {
        //Redirect to register
        RegisterView register = new RegisterView();
        register.start(stage);
    }
}
