package ui.Controllers;

import app.Main;
import app.employee.AuthValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.Database;

public class authScreenController {

    @FXML private TextField adminInitialField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    private final AuthValidation authValidation = new AuthValidation(new Database());

    @FXML
    private void handleLoginAction(ActionEvent event) {
        String init = adminInitialField.getText();
        String pwd = passwordField.getText();

//        if (authValidation.validate(init, pwd)) {
//            Main.setInitials(init);
//            Stage stage = (Stage) loginButton.getScene().getWindow();
//            stage.close();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(null);
//            alert.setContentText("Invalid credentials");
//            alert.showAndWait();
//        }
    }
}
