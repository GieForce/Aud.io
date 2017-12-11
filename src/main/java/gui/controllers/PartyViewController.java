package gui.controllers;

import aud.io.*;
import aud.io.audioplayer.AudioPlayer;
import aud.io.log.Logger;
import aud.io.rmi.ClientManager;
import gui.ButtonClass;
import gui.views.SongListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class PartyViewController implements IGUIController {
    @FXML
    private Button playButton;
    @FXML
    private VBox songContainer;
    @FXML
    private TextArea taChat;
    @FXML
    private TextField tbMessage;
    @FXML
    private Label lbPartyName;

    private String name;
    private Stage stage;
    private String key;
    private User user;

    private boolean playing;
    private boolean paused;

    private ExecutorService pool = Executors.newFixedThreadPool(3);

    private Logger logger;
    private ClientManager manager;

    public void initialize() {
        logger = new Logger("PartyView", Level.ALL, Level.SEVERE);
        manager = RmiClient.getManager();
        manager.setGuiController(this);
        try {
            for (Votable v : manager.getAllVotables()) {
                manager.addMedia(v);
                setHboxSong(v);
            }
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    private void setupParty() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                songContainer.getChildren().clear();
                Party p = manager.getParty();
                for (Votable v : manager.getPartyVotables()) {
                    setHboxSong(v);
                }
                System.out.println("Ik zit erin");
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPartyInfo() {
        lbPartyName.setText(name);
        printSystemMessage("Party key is " + key);
    }

    public void sendMessage(ActionEvent actionEvent) {
        printUserMesage();
    }

    private void printUserMesage() {
        String message = tbMessage.getText();
        tbMessage.setText("");
        if (message != null && !Objects.equals(message, "")) {
            print(message, user);
        }
    }

    private void printSystemMessage(String msg) {
        print(msg, new TemporaryUser("System"));
    }

    private void print(String msg, User user) {
        String prev = taChat.getText();
        logger.log(Level.INFO, String.format("[%s]: %s", user.getNickname(), msg));
        if (prev == null || Objects.equals(prev, "")) {
            taChat.setText(String.format("%s: %s", user.getNickname(), msg));
        } else {
            taChat.setText(String.format("%s\n%s: %s", prev, user.getNickname(), msg));
        }
    }

    public void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            printUserMesage();
        }
    }

    public void addSongs(ActionEvent actionEvent) throws IOException {
        SongListView songListView = new SongListView();
        Stage s = new Stage();
        songListView.start(s);
    }

    public void voteUp(ActionEvent actionEvent) {
        try {
            ButtonClass btn = (ButtonClass) actionEvent.getSource();
            Votable v = (Votable) btn.getObj();
            manager.voteOnVotable(v, Vote.LIKE);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public void voteDown(ActionEvent actionEvent) {
        try {
            ButtonClass btn = (ButtonClass) actionEvent.getSource();
            Votable v = (Votable) btn.getObj();
            manager.voteOnVotable(v, Vote.DISLIKE);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public void voteSkip(ActionEvent actionEvent) {
        //TODO: Get song and add vote
    }

    public void playSong(ActionEvent actionEvent) {
        try {
            if (!playing) {
                if (paused) {
                    manager.resumePlaying();
                    System.out.println("Resumed playing");
                    setPaused();
                } else {
                    manager.play();
                    System.out.println("Started playing");
                    setPaused();
                }
            } else if (playing) {
                manager.pause();
                System.out.println("Paused");
                setPlaying();
            }
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    private void setPlaying() {
        playing = false;
        paused = true;
        playButton.setText("▶");
    }

    private void setPaused() {
        playing = true;
        paused = false;
        playButton.setText("\u23F8");
    }

    public void stopSong(ActionEvent actionEvent) {
        if (playing) {
            manager.stop();
            System.out.println("Stopped playing");
            setPaused();
        }
    }

    public void skipSong(ActionEvent actionEvent) {
        try {
            if (playing) {
                manager.play();
                System.out.println("Skipped song");
                setPaused();
            }
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

//    public void toggleSong(ActionEvent actionEvent) {
//        ButtonClass btn = (ButtonClass) actionEvent.getSource();
//        Votable v = (Votable) btn.getObj();
//        boolean isSame = false;
//        if(playingVotable != null) isSame = playingVotable.equals(v);
//        if (playingVotable == null) {
//            playVotable(btn, v);
//        } else {
//            if (!playing) {
//                if (paused) player.play();
//                else {
//                    setPaused(btn);
//                }
//            } else {
//                if(isSame) setPaused(btn);
//                else {
//                    player.pause();
//                    playVotable(btn, v);
//                }
//            }
//        }
//    }

//    private void playVotable(ButtonClass btn, Votable v) {
//        player.play(v);
//        if(playingButton != null) playingButton.setText("▶");
//        btn.setText("\u23F8");
//        btn.setPadding(new Insets(4, 4, 8, 4));
//        playing = true;
//        paused = false;
//        playingVotable = v;
//        playingButton = btn;
//    }
//
//    private void setPaused(ButtonClass btn) {
//        player.pause();
//        btn.setText("▶");
//        btn.setPadding(new Insets(4, 4, 4, 4));
//        paused = true;
//        playing = false;
//    }

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
//        b.setOnAction(this::toggleSong);
        b.setStyle("-fx-background-color: black; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
        b.setText(" ");
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
        //Votable label
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.TOP_RIGHT);
        labelBox.minWidth(300);
        Label lbVotable = new Label();
        lbVotable.setAlignment(Pos.TOP_LEFT);
        lbVotable.setText(v.getVotesString());
        lbVotable.setTextFill(Paint.valueOf("#f5e9be"));
        lbVotable.setTextAlignment(TextAlignment.RIGHT);
        HBox.setHgrow(lbVotable, Priority.ALWAYS);
        lbVotable.setFont(new Font(22));
        lbVotable.setPadding(new Insets(0, 5, 5, 5));
        labelBox.setPadding(new Insets(0, 10, 0, 0));
        labelBox.getChildren().add(lbVotable);
        //Votebuttons
        HBox voteButtons = new HBox();
        voteButtons.setAlignment(Pos.CENTER_RIGHT);
        voteButtons.minWidth(50);
        VBox vchild = new VBox();
        //btnUp
        ButtonClass btnUp = new ButtonClass(v);
        btnUp.minHeight(27);
        btnUp.minWidth(27);
        btnUp.setOnAction(this::voteUp);
        btnUp.setStyle("-fx-background-color: black; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
        btnUp.setText("▲");
        btnUp.setTextFill(Paint.valueOf("#f5e9be"));
        btnUp.setPadding(new Insets(5, 5, 5, 5));
        vchild.getChildren().add(btnUp);
        VBox.setMargin(btnUp, new Insets(0, 0, 5, 0));
        //btnDown
        ButtonClass btnDown = new ButtonClass(v);
        btnDown.minHeight(27);
        btnDown.minWidth(27);
        btnDown.setOnAction(this::voteDown);
        btnDown.setStyle("-fx-background-color: black; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
        btnDown.setText("▼");
        btnDown.setTextFill(Paint.valueOf("#f5e9be"));
        btnDown.setPadding(new Insets(5, 5, 5, 5));
        vchild.getChildren().add(btnDown);
        //btnSkip
//        Button btnSkip = new Button();
//        btnSkip.minHeight(45);
//        btnSkip.minWidth(45);
//        btnSkip.setOnAction(this::voteSkip);
//        btnSkip.setStyle("-fx-background-color: black; -fx-border-radius: 50 50 50 50; -fx-background-radius: 50 50 50 50;");
//        Image image = new Image("file:img/fastforward.png");
//        btnSkip.setGraphic(new javafx.scene.image.ImageView(image));
//        btnSkip.setPadding(new Insets(0,5,0,5));
//        vchild.getChildren().add(btnSkip);
        HBox.setMargin(vchild, new Insets(0, 0, 0, 10));
        voteButtons.getChildren().add(vchild);
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
        box.getChildren().addAll(b, childHbox, labelBox, voteButtons);
//        box.getChildren().addAll(childHbox, labelBox, voteButtons);
        VBox.setMargin(box, new Insets(0, 0, 6, 0));
        box.setPadding(new Insets(6, 6, 6, 6));
        songContainer.getChildren().add(box);
    }

    @Override
    public void update() {
        System.out.println("Update");
        setupParty();
    }
}

