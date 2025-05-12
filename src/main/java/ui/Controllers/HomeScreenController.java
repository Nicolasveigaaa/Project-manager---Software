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
import java.util.stream.Collectors;

// Logic imports
import app.Main;
import app.project.ProjectService;

public class HomeScreenController {
    private final Database db = new Database();
    private final ProjectService projectService = Main.getProjectService();

    @FXML public ListView<Project> projectsListView;
    @FXML private Label projectsCountLabel;
    @FXML private Label initialsLabel;
    @FXML private Button openProject;

    private User currentUser;

    // Loads all projects from the database and sets them in the ListView
    private void loadProjects() {
        projectsListView.getItems().clear();
        List<Project> projects = db.getAllProjects();

        projectsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String initials = currentUser.getInitials();
        int numbOfProjects = 0;

        for (Project project : projects) {
            if (project.getMemberInitials().contains(initials)) {
                projectsListView.getItems().add(project);
                numbOfProjects++;
            }
        }

        projectsCountLabel.setText(String.valueOf(numbOfProjects));
    }

    // Sets the logged-in user information in the UI
    public void setLoggedInUser(User user) {
        this.currentUser = user;
        initialsLabel.setText(user.getInitials());
        loadProjects();
    }

    @FXML
    public void initialize() {
        openProject.disableProperty().bind(
                projectsListView.getSelectionModel()
                        .selectedItemProperty()
                        .isNull());
    }

    @FXML
    public void handleCreateProject(ActionEvent event) {
        ProjectCreationScreenController.show();
        loadProjects();
    }

    @FXML
    public void handleLogoutAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent loginRoot = FXMLLoader.load(
                    getClass().getResource("/ui/FXML/authScreen.fxml"));
            stage.setScene(new Scene(loginRoot, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenProject(ActionEvent event) {
        Project selected = projectsListView
                .getSelectionModel()
                .getSelectedItem();
        if (selected == null) return;

        String projectID = selected.getProjectID();
        Optional<Project> projectData = projectService.openProject(projectID);
        openProjectWindow(projectData);
    }

    public void openProjectWindow(Optional<Project> projectData) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/FXML/selectedProject.fxml"));
            Parent projectWindow = loader.load();

            Stage stage = (Stage) openProject.getScene().getWindow();
            Scene homeScene = stage.getScene(); // gem Home-scenen

            ProjectScreenController psc = loader.getController();
            psc.setupUI(projectData);

            // Returner til HomeScreen og genskab bruger
            psc.setReturnHandler(() -> {
                stage.setScene(homeScene);
                setLoggedInUser(currentUser);
            });

            stage.setScene(new Scene(projectWindow, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test-accessors (optional)
    public List<String> getDisplayedProjectNames() {
        return projectsListView.getItems().stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());
    }

    public int getDisplayedProjectCount() {
        return projectsListView.getItems().size();
    }

    public String getCountLabelText() {
        return projectsCountLabel.getText();
    }

    public String getInitialsText() {
        return initialsLabel.getText();
    }
}