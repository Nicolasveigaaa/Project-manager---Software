package ui.Controllers;

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
import persistence.Database;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the "Create Project" dialog.
 * Presents a multi-select list of users via checkboxes.
 */
public class ProjectCreationScreenController {

    @FXML private TextField projectNameField;
    @FXML private ListView<String> usersListView;

    private final Database db = new Database();

    @FXML
    private void initialize() {
        // Load all user initials from the database
        List<String> initials = db.getAllUserInitials();
        ObservableList<String> items = FXCollections.observableArrayList(initials);
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

    /**
     * Called when the user clicks "Create".
     * Validates input, creates the project, and closes the dialog.
     */
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

        // Persist the new project in the database
        db.createProject(name, List.copyOf(selected));

        // Close the dialog window
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();

    }

    /**
     * Called when the user clicks "Cancel".
     * Closes the dialog without saving.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Static helper to show this screen as a modal dialog.
     */
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