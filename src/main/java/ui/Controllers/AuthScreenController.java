package ui.Controllers;

import app.Main;
import app.employee.AuthValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import persistence.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

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

    private final AuthValidation authValidation = new AuthValidation(new Database());

    // Add scanner for pressed enter to validate login
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLoginAction(null);
        }
    }

    @FXML
    private void handleLoginAction(ActionEvent event) {
        String init = adminInitialField.getText().trim();
        String pwd = passwordField.getText().trim();

        validate(init, pwd);
    }

    private void validate(String init, String pwd) {
        if (authValidation.validateLogin(init, pwd)) {
            Main.initiateSoftware(init);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            try {
                Parent homeRoot = FXMLLoader.load(
                        getClass().getResource("/ui/FXML/homeScreen.fxml")
                );
                stage.setScene(new Scene(homeRoot, 600, 400));
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials");
            alert.showAndWait();
        }
    }
}
