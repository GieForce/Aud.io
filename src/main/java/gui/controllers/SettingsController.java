package gui.controllers;

import aud.io.Settings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.management.remote.rmi.RMIServer;
import java.util.ArrayList;

public class SettingsController {
    @FXML private Label lbPlaylistName;
    @FXML private VBox blockedSongsContainer;
    @FXML private VBox blockedArtistsContainer;
    @FXML private TextField tbSongs;
    @FXML private TextField tbArtists;

    private Settings settings;
    private ArrayList<String> removeBlockedSongs = new ArrayList();
    private ArrayList<String> removeBlockedArtists = new ArrayList();

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        lbPlaylistName.setText(RmiClient.getManager().getParty().getName());
        settings = RmiClient.getManager().getSettings();
        if (settings != null) {
            for (String song : settings.getBlockedSongs()) {
                Platform.runLater(() -> addSongUI(song));
            }
            for (String artist : settings.getBlockedArtists()) {
                Platform.runLater(() -> addArtistUI(artist));
            }
        } else {
            settings = new Settings();
        }
    }

    public void addArtists(ActionEvent actionEvent) {
        String[] artists = tbArtists.getText().split(",");
        for (String artist : artists) {
            settings.blockArtist(artist);
            Platform.runLater(() -> addArtistUI(artist));
        }
        tbArtists.setText("");
    }

    public void addSongs(ActionEvent actionEvent) {
        String[] songs = tbSongs.getText().split(",");
        for (String song : songs) {
            settings.blockSong(song);
            Platform.runLater(() -> addSongUI(song));
        }
        tbSongs.setText("");
    }

    public void removeBlockedArtists(ActionEvent actionEvent) {
        for (String artist : removeBlockedArtists) {
            settings.unblockArtist(artist);
        }
        removeBlockedArtists.clear();
        drawArtists();
    }

    public void removeBlockedSongs(ActionEvent actionEvent) {
        for (String song : removeBlockedSongs) {
            settings.unblockSong(song);
        }
        removeBlockedSongs.clear();
        drawSongs();
    }

    private void drawSongs() {
        blockedSongsContainer.getChildren().clear();
        for (String song : settings.getBlockedSongs()) {
            addSongUI(song);
        }
    }

    private void drawArtists() {
        blockedArtistsContainer.getChildren().clear();
        for (String artist : settings.getBlockedArtists()) {
            addArtistUI(artist);
        }
    }

    private void addSongUI(String song) {
        CheckBox cbox = new CheckBox();
        cbox.setText(song);
        cbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                removeBlockedSongs.add(song);
            } else {
                removeBlockedSongs.remove(song);
            }
        });
        blockedSongsContainer.getChildren().add(cbox);
    }

    private void addArtistUI(String artist) {
        CheckBox cbox = new CheckBox();
        cbox.setText(artist);
        cbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                removeBlockedArtists.add(artist);
            } else {
                removeBlockedArtists.remove(artist);
            }
        });
        blockedArtistsContainer.getChildren().add(cbox);
    }

    public void saveSettings(ActionEvent actionEvent) {
        RmiClient.getManager().alterSettings(settings);
        stage.close();
    }
}
