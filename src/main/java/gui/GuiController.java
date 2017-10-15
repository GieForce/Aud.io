package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


public class GuiController implements Initializable {

    @FXML
    private ListView trackListview;

    public void getSongs()
    {
        System.out.println("Songs");
    }


    public void initialize(URL location, ResourceBundle resources) {

    }
}
