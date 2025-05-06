// [Written mainly by s246060 additions and rewrites s244706] //

package ui.Controllers;

// Folder imports
import domain.User;
import domain.Project;
import persistence.Database;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

// Java imports
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import app.employee.AuthValidation;
import app.project.ProjectService;

// Controller that handles HomeScreen.fxml
public class HomeScreenController {
    private final Database db = new Database();

    @FXML
    private ListView<Project> projectsListView;
    @FXML
    private Label projectsCountLabel;

    @FXML
    private Label initialsLabel;
    @FXML
    private Label roleLabel;

    @FXML
    private Button openProject;

    // Loads all projects from the database and sets them in the ListView (s244706 + s246060)
    private void loadProjects() {
        // Clear current projects from screen
        projectsListView.getItems().clear();

        List<Project> projects = db.getAllProjects();

        // enforce single‐selection
        projectsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Now before showing the user the projects, check if the user is part of it
        // (Get current user)
        User user = AuthValidation.getCurrentUser();
        String initials = user.getInitials();

        // Check if the user is part of the project
        for (Project project : projects) {
            if (project.getMemberInitials().contains(initials)) {
                projectsListView.getItems().add(project);
            }
        }

        projectsCountLabel.setText(String.valueOf(projects.size()));
    }

    // Sets the logged-in user information in the UI
    public void setLoggedInUser(User user) {
        initialsLabel.setText(user.getInitials());
        roleLabel.setText(user.getRole());

        loadProjects();
    }

    // Loads screen after fetching relevant information
    @FXML
    private void initialize() {
        loadProjects();

        // Disable "Open" until something is selected:
        openProject.disableProperty().bind(
                projectsListView.getSelectionModel()
                        .selectedItemProperty()
                        .isNull());
    }

    // Opens the Create Project dialog, then refreshes the project list.
    @FXML
    private void handleCreateProject(ActionEvent event) {
        ProjectCreationScreenController.show();
        loadProjects();
    }

    // Logs user out by redirecting back to the login screen
    @FXML
    private void handleLogoutAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent loginRoot = FXMLLoader.load(
                    getClass().getResource("/ui/FXML/authScreen.fxml"));
            stage.setScene(new Scene(loginRoot, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open the selected project (s244706)
    @FXML
    private void handleOpenProject(ActionEvent event) {
        Project selected = projectsListView
                .getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            // nothing to open
            return;
        }

        // Suppose your Project has a getId() method:
        String projectID = selected.getProjectID();

        System.out.println("Opening project " + projectID);

        Optional<Project> projectData = ProjectService.openProject(projectID);
        System.out.println("Project name: " + projectData.get().getProjectName());

        openProjectWindow(projectData);
    }

    // Open the project window
    @FXML
    private void openProjectWindow(Optional<Project> projectData) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/FXML/selectedProject.fxml")
            );
            Parent projectWindow = loader.load();

            Stage stage = (Stage) openProject.getScene().getWindow();
            stage.setScene(new Scene(projectWindow, 600, 400));
            ProjectScreenController psc = loader.getController();
            psc.setupUI(projectData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
