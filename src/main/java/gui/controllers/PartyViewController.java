package gui.controllers;

import aud.io.IPlayer;
import aud.io.TemporaryUser;
import aud.io.User;
import aud.io.Votable;
import aud.io.audioplayer.AudioPlayer;
import aud.io.audioplayer.Track;
import aud.io.log.Logger;
import aud.io.memory.MemoryMedia;
import gui.views.SongListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class PartyViewController {
    @FXML
    private Button btnToggleSong;
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

    private IPlayer player;
    private ExecutorService pool = Executors.newFixedThreadPool(3);

    private Logger logger;
    private String path = "src/main/resources/audio/Demo.mp3";
    private Votable votable;

    public void initialize() {
        logger = new Logger("PartyView", Level.ALL, Level.SEVERE);
        boolean found = new NativeDiscovery().discover();
        AudioMediaPlayerComponent vlcplayer = new AudioMediaPlayerComponent();
        player = new AudioPlayer(pool, vlcplayer);
        votable = new Track(new MemoryMedia(path));
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
        //TODO: Get song and add vote
    }

    public void voteDown(ActionEvent actionEvent) {
        //TODO: Get song and add vote
    }

    public void voteSkip(ActionEvent actionEvent) {
        //TODO: Get song and add vote
    }

    public void toggleSong(ActionEvent actionEvent) {
        if (!playing) {
            if (paused) player.play();
            else player.play(votable);
            btnToggleSong.setText("\u23F8");
            btnToggleSong.setPadding(new Insets(4,4,8,4));
            playing = true;
            paused = false;
        } else {
            player.pause();
            btnToggleSong.setText("▶");
            btnToggleSong.setPadding(new Insets(4,4,4,4));
            paused = true;
            playing = false;
        }
    }
}

