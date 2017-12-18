package gui.controllers;

import aud.io.Vote;
import aud.io.log.Logger;
import aud.io.rmi.ClientManager;
import aud.io.Votable;
import gui.ButtonClass;
import gui.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.Objects;
import java.util.logging.Level;

public class SongListController {
    @FXML
    private VBox songContainer;
    @FXML
    private TextField tbSongName;
    private Stage stage;
    private ClientManager manager;

    private Logger logger;


    public void initialize() throws RemoteException {
        Logger logger = new Logger("SonglistController", Level.SEVERE, Level.SEVERE);
        manager = RmiClient.getManager();
        for (Votable v : manager.getAllVotables()) {
            setHboxSong(v);
        }
        //TODO: addMedia function has changed, please revise!!
        //manager.addMedia(null);
        //for (Votable v : manager.getVotables()) {
        //    setHboxSong(v);
        //}
    }

    public void enterPressed(KeyEvent keyEvent) {

    }

    public void searchSong(ActionEvent actionEvent) throws RemoteException {
        songContainer.getChildren().clear();
        String name = tbSongName.getText();
        if (!Objects.equals(name, "") || name != null) {
            //Doesn't work yet
            for (Votable v :manager.searchVotablesWithSearchTerm(name)) {
                setHboxSong(v);
            }
        } else {
            Message.Show("Error", "Please enter a song name");
        }
    }

    private void setHboxSong(Votable v) {
        Font defFont = new Font(24);
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.setMinWidth(500);
        box.setStyle("-fx-background-color: #34454d; -fx-background-radius: 50 50 50 50;");
        //Button
        ButtonClass b = new ButtonClass(v);
        b.setMaxHeight(50);
        b.setMinHeight(50);
        b.setMaxWidth(50);
        b.setMinWidth(50);
        b.setMnemonicParsing(false);
        b.setOnAction(this::addSong);
        b.setStyle("-fx-background-color: black; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
        b.setText("+");
        b.setTextAlignment(TextAlignment.CENTER);
        b.setTextFill(Paint.valueOf("#f5e9be"));
        b.setPadding(new Insets(3, 3, 3, 3));
        b.setFont(defFont);
        //Hbox child
        HBox childHbox = new HBox();
        childHbox.setAlignment(Pos.CENTER_LEFT);
        //Vbox child
        VBox childVbox = new VBox();
        //Label
        Label lblSongName = new Label();
        lblSongName.setText(v.getName());
        lblSongName.setTextFill(Paint.valueOf("#f5e9be"));
        lblSongName.setFont(defFont);
        //Label
        //TODO: Change this to album or artist
        Label lblSongLegth = new Label();
        lblSongLegth.setText(String.valueOf(v.getLength()));
        lblSongLegth.setTextFill(Paint.valueOf("#f5e9be"));
        lblSongLegth.setFont(defFont);
        childVbox.getChildren().addAll(lblSongName, lblSongLegth);
        VBox.setMargin(lblSongName, new Insets(0, 4, 0, 4));
        VBox.setMargin(lblSongLegth, new Insets(0, 4, 0, 8));
        childHbox.getChildren().add(childVbox);
        box.getChildren().addAll(b, childHbox);
        VBox.setMargin(box, new Insets(0, 0, 6, 0));
        box.setPadding(new Insets(6, 6, 6, 6));
        songContainer.getChildren().add(box);
    }

    public void addSong(ActionEvent actionEvent) {
        try {
            ButtonClass btn = (ButtonClass) actionEvent.getSource();
            Votable v = (Votable) btn.getObj();
            manager.addMedia(v);
            //TODO: Update UI
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
