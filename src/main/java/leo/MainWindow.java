package leo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Leo leo;

    @FXML
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    @FXML
    private Image leoImage = new Image(this.getClass().getResourceAsStream("/images/Leo.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setLeo(Leo d) {
        leo = d;

        if (leo.getStartupMessage() != null) {
            dialogContainer.getChildren().add(
                    DialogBox.getErrorDialog(leo.getStartupMessage(), leoImage)
            );
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            String response = leo.getResponse(input);
            dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                    DialogBox.getLeoDialog(response, leoImage));
        } catch (LeoException e) {
            dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                    DialogBox.getErrorDialog(e.getMessage(), leoImage));
        }
        userInput.clear();

        if (leo.getExit()) {
            Platform.exit();
        }
    }
}
