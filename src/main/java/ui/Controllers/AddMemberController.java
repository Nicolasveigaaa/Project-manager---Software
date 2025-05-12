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
import javafx.stage.Modality;
import javafx.stage.Stage;

// Java imports
import java.io.IOException;
import java.util.List;

// Logic
import app.Main;

// Domain
import domain.Project;


// Controller that handles ProjectCreationScreen.fxml
public class AddMemberController {
    private final Database db = Main.getDatabase();

    private Project projectData;

    @FXML private ListView<String> usersListView;

    public void addUserButton(Project project) {
        this.projectData = project;

        // Load all user initials from except current user
        List<String> availableUsers = db.getAllUserInitials();

        // Get active users
        List<String> activeUsers = projectData.getMemberInitials();
        //List<String> availableUsers = new ArrayList<>();
        for (String userInitials : activeUsers){
            for (int i = 0; i < availableUsers.size() ; i++){
                if (userInitials.equals(availableUsers.get(i))) {
                    availableUsers.remove(i);
                }
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
        List<String> selected = usersListView.getSelectionModel().getSelectedItems();

        if (selected.isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Please select at least one user.")
                    .showAndWait();
            return;
        }
        // Send the selected users to the project by project ID
        String ID = projectData.getProjectID();

        // Now add the members to the project through the database
        for (String initials : selected) {
            db.addUserToProject(ID, initials);
        }

        
    Stage stage = (Stage) usersListView.getScene().getWindow();
        stage.close();
    }

    // Closes modal dialog without creating a project
    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) usersListView.getScene().getWindow();
        stage.close();
    }


    // Shows screen as modal instead of screen
    public static void show() {
        try {
            Parent root = FXMLLoader.load(
                    AddMemberController.class
                            .getResource("/ui/FXML/addMemberController.fxml")
            );
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add new member");
            dialog.setScene(new Scene(root, 300, 350));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
