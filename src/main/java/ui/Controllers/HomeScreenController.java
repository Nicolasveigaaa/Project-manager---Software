package ui.Controllers;

import app.employee.AuthValidation;
import domain.Employee;
import domain.Project;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import persistence.Database;

import java.io.IOException;
import java.util.List;

public class HomeScreenController {

    private final Database db = new Database();

    @FXML private ListView<Project> projectsListView;
    @FXML private Label projectsCountLabel;

    // Initials label to show the logged-in user's initials
    @FXML private Label initialsLabel;
    @FXML private Label roleLabel;


    /** Called by AuthScreenController just after load() */
    public void setLoggedInUser(Employee user) {
        // immediately update your labels
        initialsLabel.setText(user.getInitials());
        roleLabel   .setText(user.getRole());
        // and if you want, kick off any post-login loads:
        loadProjects();
    }

    /**
     * Called by the FXML loader after the root element is processed.
     * Loads existing projects into the ListView and updates the count label.
     */
    @FXML
    private void initialize() {
        loadProjects();
    }

    /**
     * Fetches all projects from the database and displays them.
     */
    private void loadProjects() {
        List<Project> projects = db.getAllProjects();
        ObservableList<Project> items = projectsListView.getItems();
        items.setAll(projects);
        projectsCountLabel.setText(String.valueOf(projects.size()));
    }

    /**
     * Opens the Create Project dialog, then refreshes the project list.
     */
    @FXML
    private void handleCreateProject(ActionEvent event) {
        ProjectCreationScreenController.show();
        loadProjects();
    }

    /**
     * Logs the user out by returning to the authentication screen.
     */
    @FXML
    private void handleLogoutAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent loginRoot = FXMLLoader.load(
                    getClass().getResource("/ui/FXML/authScreen.fxml")
            );
            stage.setScene(new Scene(loginRoot, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
