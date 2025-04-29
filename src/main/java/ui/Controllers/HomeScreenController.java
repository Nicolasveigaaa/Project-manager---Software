package ui.Controllers;

// Folder imports
import domain.Employee;
import domain.Project;
import persistence.Database;

// JavaFX imports
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

// Java imports
import java.io.IOException;
import java.util.List;


// Controller that handles HomeScreen.fxml
public class HomeScreenController {
    private final Database db = new Database();


    @FXML private ListView<Project> projectsListView;
    @FXML private Label projectsCountLabel;

    @FXML private Label initialsLabel;
    @FXML private Label roleLabel;


    // Loads all projects from the database and sets them in the ListView
    private void loadProjects() {
        List<Project> projects = db.getAllProjects();
        ObservableList<Project> items = projectsListView.getItems();
        items.setAll(projects);
        projectsCountLabel.setText(String.valueOf(projects.size()));
    }

    // Sets the logged-in user information in the UI
    public void setLoggedInUser(Employee user) {
        initialsLabel.setText(user.getInitials());
        roleLabel   .setText(user.getRole());

        loadProjects();
    }


    // Loads screen after fetching relevant information
    @FXML
    private void initialize() {
        loadProjects();
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
                    getClass().getResource("/ui/FXML/authScreen.fxml")
            );
            stage.setScene(new Scene(loginRoot, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
