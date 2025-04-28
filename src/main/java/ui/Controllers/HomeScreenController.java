package ui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class HomeScreenController {

    @FXML
    private Button logoutButton;

    @FXML
    private void handleLogoutAction(ActionEvent event) {
        // Get the current window (stage)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            // Load the login (auth) screen FXML
            Parent loginRoot = FXMLLoader.load(
                getClass().getResource("/ui/FXML/authScreen.fxml")
            );
            // Set the scene back to login, size as needed
            stage.setScene(new Scene(loginRoot, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
