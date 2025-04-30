package ui.Controllers;

// Folder imports
import persistence.Database;

// JavaFX imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Java imports
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.Main;
import app.employee.AuthValidation;
import app.project.ProjectService;


// Controller that handles ProjectCreationScreen.fxml
public class ProjectCreationScreenController {
    private final Database db = Main.getDatabase();

    @FXML private TextField projectNameField;
    @FXML private ListView<String> usersListView;


    @FXML
    private void initialize() {
        // Load all user initials from the database except current user
        List<String> initials = db.getAllUserInitials();

        // Get current user
        String currentUserInitials = AuthValidation.getCurrentUser().getInitials();
        List<String> availableUsers = new ArrayList<>();

        for (String userInitials : initials) {
            if (!userInitials.equals(currentUserInitials)) {
                availableUsers.add(userInitials);
            }
        }

        ObservableList<String> items = FXCollections.observableArrayList(availableUsers);
        usersListView.setItems(items);

        // Allow multiple selection
        usersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Use CheckBoxListCell to render each item with a checkbox
        usersListView.setCellFactory(CheckBoxListCell.forListView(item -> {
            SimpleBooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    usersListView.getSelectionModel().select(item);
                } else {
                    usersListView.getSelectionModel().clearSelection(usersListView.getItems().indexOf(item));
                }
            });
            return observable;
        }));
    }
    

    // Validates inputs and creates a new project in the persistence database
    @FXML
    private void handleCreate(ActionEvent event) {
        String name = projectNameField.getText().trim();
        List<String> selected = usersListView.getSelectionModel().getSelectedItems();

        if (name.isEmpty() || selected.isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Please enter a project name and select at least one user.")
                    .showAndWait();
            return;
        }

        // Send the selected users to the project by project ID
        String ID = ProjectService.addProject(name);

        // Now add the members to the project through the database
        for (String initials : selected) {
            db.addUserToProject(ID, initials);
        }

        // Close the dialog window
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }

    // Closes modal dialog without creating a project
    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }

    // Shows screen as modal instead of screen
    public static void show() {
        try {
            Parent root = FXMLLoader.load(
                    ProjectCreationScreenController.class
                            .getResource("/ui/FXML/ProjectCreationScreen.fxml")
            );
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Create New Project");
            dialog.setScene(new Scene(root, 300, 350));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}