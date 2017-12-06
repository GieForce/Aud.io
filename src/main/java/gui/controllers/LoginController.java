package gui.controllers;

import aud.io.Hashing.Hash;
import aud.io.User;
import aud.io.mongo.Connection;
import aud.io.rmi.ClientManager;
import aud.io.RegisteredUser;
import gui.views.MenuView;
import gui.Message;
import gui.views.RegisterView;
import gui.views.TempUserView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
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

    public boolean login(ActionEvent actionEvent) throws IOException {

        RegisteredUser user;
        String username = tbUsername.getText();
        String password = tbPassword.getText();
        String hash;

           MongoCollection users = Connection.connect().getCollection("users");

           RegisteredUser regUser = users.findOne("{ nickname : "+ username + " , {password :" + password + "}").as(RegisteredUser.class);


            System.out.println("Hallo");
           String hashedPassword = regUser.getMongoPassword();

            Hash h = new Hash();
            h.checkPass(password, hashedPassword);

            if (regUser != null)
            {
                return true;
            }
            else return false;





       /* MongoCollection users = Connection.connect().getCollection("users");
        MongoCursor<RegisteredUser> usersSearch = users.find("{ nickname : 'joel' }").as(RegisteredUser.class);

        while (usersSearch.hasNext())
        {
            user = usersSearch.next();

            System.out.println(user.getNickname());

        }*/




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
