package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LoginController {
    public ImageView imageBackground;
    public VBox vboxRoot;

    private javax.swing.text.html.ImageView background;
    private javax.swing.text.html.ImageView logo;
    private Label label;
    private TextField txId;
    private Button btnJoin;

    public void Initialize() {
        imageBackground.setFitWidth(240);
        imageBackground.setPreserveRatio(true);

        
    }
}
