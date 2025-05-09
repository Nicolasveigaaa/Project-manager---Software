// [Written by s246060] //

package ui.Controllers;

// Folder import
import app.employee.AuthValidation;
import domain.User;
import persistence.Database;
import ui.BaseController;
// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

// Java imports
import java.io.IOException;

// Controls the authScreen.fxml
public class AuthScreenController {
    @FXML
    private TextField adminInitialField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        adminInitialField.setOnKeyPressed(this::onEnterPressed);
    }

    // Instance of the AuthValidation class to validate login credentials
    private final AuthValidation authValidation = new AuthValidation(new Database());

    // Add scanner for pressed enter to validate login
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLoginAction(null);
        }
    }

    public void login(String initials) {
        String init = initials;

        if (initials == null) {
            init = adminInitialField.getText().trim();
        }

        if (authValidation.validateLogin(init)) { //, pwd)) {
            User user = AuthValidation.getCurrentUser();  // now has the Employee

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/ui/FXML/homeScreen.fxml")
                );
                Parent homeRoot = loader.load();

                // grab the controller instance and hand it the Employee
                HomeScreenController homeCtrl = loader.getController();
                homeCtrl.setLoggedInUser(user);

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(homeRoot, 600, 400));

                BaseController.pushHistory("/ui/FXML/homeScreen.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid credentials").showAndWait();
        }
    }

    @FXML
    private void handleLoginAction(ActionEvent ev) {
        // Get initials from the text field
        String initials = adminInitialField.getText().trim();
        login(initials);
    }
}