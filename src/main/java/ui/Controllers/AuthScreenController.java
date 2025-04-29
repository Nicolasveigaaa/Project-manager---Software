package ui.Controllers;

// Folder import
import app.employee.AuthValidation;
import domain.User;
import persistence.Database;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
    private PasswordField passwordField;

    @FXML
    private Button loginButton;


    @FXML
    private void initialize() {
        passwordField.setOnKeyPressed(this::onEnterPressed);
    }


    // Instance of the AuthValidation class to validate login credentials
    private final AuthValidation authValidation = new AuthValidation(new Database());

    // Add scanner for pressed enter to validate login
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLoginAction(null);
        }
    }


    @FXML
    private void handleLoginAction(ActionEvent ev) {
        String init = adminInitialField.getText().trim();
        String pwd  = passwordField.getText().trim();

        if (authValidation.validateLogin(init, pwd)) {
            User user = authValidation.getCurrentUser();  // now has the Employee

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid credentials").showAndWait();
        }
    }
}
