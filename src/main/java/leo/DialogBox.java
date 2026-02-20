
package leo;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private StackPane bubble;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.displayPicture.setVisible(true);
        db.displayPicture.setManaged(true);
        db.getBubble().getStyleClass().add("user-bubble");
        return db;
    }

    public static DialogBox getLeoDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.displayPicture.setVisible(false);
        db.displayPicture.setManaged(false);

        db.getBubble().getStyleClass().add("leo-bubble");
        db.flip();
        return db;
    }

    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.displayPicture.setVisible(false);
        db.displayPicture.setManaged(false);

        db.getBubble().getStyleClass().add("error-bubble");
        db.flip();
        return db;
    }

    public StackPane getBubble() {
        return bubble;
    }
}
