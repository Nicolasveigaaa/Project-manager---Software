// [Written mainly by s246060 additions and rewrites s244706] //

package ui.Controllers;

// Folder imports
import persistence.Database;

// Domain imports
import domain.Project;

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
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Java imports
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Logic imports
import app.Main;
import app.employee.AuthValidation;
import app.project.ProjectService;

// Controller that handles ProjectCreationScreen.fxml
public class ProjectCreationScreenController {
    private final Database db = Main.getDatabase();

    @FXML
    private TextField projectNameField;
    @FXML
    private ListView<String> usersListView;

    private boolean editMode = false;
    private Project projectToEdit;
    private List<String> currentActiveEmployees = new ArrayList<>();

    // Boolean map to keep sync between UI checkboxes and selection
    private final Map<String, BooleanProperty> userSelectionMap = new HashMap<>();

    @FXML
    private void initialize() {
        if (!editMode && projectToEdit == null) {
            loadUsers();
        }
        // List<String> initials = db.getAllUserInitials();
        // String currentUserInitials = AuthValidation.getCurrentUser().getInitials();

        // System.out.println("TEST");
        // for (String userInitials : currentActiveEmployees) {
        //     System.out.println("User Initials: " + userInitials);
        // }

        // List<String> availableUsers = new ArrayList<>();
        // for (String userInitials : initials) {
        //     if (!userInitials.equals(currentUserInitials) && !currentActiveEmployees.contains(userInitials)) {
        //         availableUsers.add(userInitials);
        //     }
        // }

        // ObservableList<String> items = FXCollections.observableArrayList(availableUsers);
        // usersListView.setItems(items);
        // usersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // // Build a map of user initials -> boolean selected
        // for (String user : items) {
        //     boolean isSelected = editMode && projectToEdit != null && projectToEdit.getMemberInitials().contains(user);
        //     SimpleBooleanProperty property = new SimpleBooleanProperty(isSelected);
        //     property.addListener((obs, oldVal, newVal) -> {
        //         if (newVal) {
        //             usersListView.getSelectionModel().select(user);
        //         } else {
        //             usersListView.getSelectionModel().clearSelection(usersListView.getItems().indexOf(user));
        //         }
        //     });
        //     userSelectionMap.put(user, property);
        // }

        // // Set checkbox renderer that binds to map
        // usersListView.setCellFactory(CheckBoxListCell.forListView(user -> userSelectionMap.get(user)));

        // // Pre-fill data if editing
        // if (editMode && projectToEdit != null) {
        //     projectNameField.setText(projectToEdit.getProjectName());
        //     for (String member : projectToEdit.getMemberInitials()) {
        //         usersListView.getSelectionModel().select(member);
        //     }
        // }
    }

    // Validates inputs and creates a new project in the persistence database
    @FXML
    private void handleCreate(ActionEvent event) {
        String name = projectNameField.getText().trim();
        List<String> selected = new ArrayList<>(usersListView.getSelectionModel().getSelectedItems());

        // Always ensure current user is in the list
        String currentUser = AuthValidation.getCurrentUser().getInitials();
        if (!selected.contains(currentUser)) {
            selected.add(currentUser);
        }

        // Add all the already selected users to the list
        for (String user : currentActiveEmployees) {
            if (!selected.contains(user)) {
                selected.add(user);
            }
        }

        if (name.isEmpty() && selected.isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Please enter a project name and select at least one user.")
                    .showAndWait();
            return;
        } else if (name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Please enter a project name")
                    .showAndWait();
            return;
        } else if (selected.isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Please select at least one user.")
                    .showAndWait();
            return;
        }

        if (editMode && projectToEdit != null) {
            // Update existing project
            projectToEdit.setProjectName(name);
            projectToEdit.getMemberInitials().clear();
            projectToEdit.getMemberInitials().addAll(selected);

        } else {
            // Send the selected users to the project by project ID
            String ID = ProjectService.addProject(name);

            // Now add the members to the project through the database
            for (String initials : selected) {
                db.addUserToProject(ID, initials);
            }
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

    public void loadUsers() {
        List<String> initials = db.getAllUserInitials();
        String currentUserInitials = AuthValidation.getCurrentUser().getInitials();

        List<String> availableUsers = new ArrayList<>();
        for (String userInitials : initials) {
            if (!userInitials.equals(currentUserInitials) && !currentActiveEmployees.contains(userInitials)) {
                availableUsers.add(userInitials);
            }
        }

        ObservableList<String> items = FXCollections.observableArrayList(availableUsers);
        usersListView.setItems(items);
        usersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        for (String user : items) {
            boolean isSelected = editMode && projectToEdit != null && projectToEdit.getMemberInitials().contains(user);
            SimpleBooleanProperty property = new SimpleBooleanProperty(isSelected);
            property.addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    usersListView.getSelectionModel().select(user);
                } else {
                    usersListView.getSelectionModel().clearSelection(usersListView.getItems().indexOf(user));
                }
            });
            userSelectionMap.put(user, property);
        }

        usersListView.setCellFactory(CheckBoxListCell.forListView(user -> userSelectionMap.get(user)));

        if (editMode && projectToEdit != null) {
            projectNameField.setText(projectToEdit.getProjectName());
            for (String member : projectToEdit.getMemberInitials()) {
                usersListView.getSelectionModel().select(member);
            }
        }
    }

    // Shows screen as modal instead of screen
    public static void show() {
        try {
            Parent root = FXMLLoader.load(
                    ProjectCreationScreenController.class
                            .getResource("/ui/FXML/ProjectCreationScreen.fxml"));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Create New Project");
            dialog.setScene(new Scene(root, 300, 350));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Enables edit mode for project editing
    public void setEditMode(Project project) {
        this.editMode = true;
        this.projectToEdit = project;
        this.currentActiveEmployees = project.getMemberInitials();
    }
}