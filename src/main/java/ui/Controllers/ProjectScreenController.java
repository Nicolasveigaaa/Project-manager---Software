// [Written by s244706] //

package ui.Controllers;

import java.util.Optional;

import app.Main;
import app.employee.AuthValidation;
import app.project.ProjectService;
import domain.Project;
import domain.User;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProjectScreenController {
    // ADD FXML
    @FXML
    private Label projectNameText;
    @FXML
    private Label projectAdminInitialsText;
    @FXML
    private Button viewTotalHours;

    // Require the project service from the main / avoiding using static references, hehe, smart right.
    private final ProjectService projectService = Main.getProjectService();

    private String projectTitle = "Project Name";
    private String projectAdminInitials = "Admin Initial";
    private String currentUser = "TestUser";
    private Project projectData;

    @FXML
    private void initialize() {
        // Disable "View Total hours unless admin / manager"
        System.out.println("Opening Project Screen");
        // Get user initials
        User user = AuthValidation.getCurrentUser();
        String initials = user.getInitials();
        this.currentUser = initials;

        System.out.println("User initials: " + initials);
    }

    public void setupUI(Optional<Project> projectData) {
        if (projectData.isPresent()) {
            // Get project name and admin initials
            Project project = projectData.get();
            this.projectTitle = "Project Name: " + project.getProjectName();
            this.projectAdminInitials = "Manager: " + project.getProjectLeaderInitials();

            // Setup activity list UI
            updateProjectActivityList(project);

            // Update UI
            projectNameText.setText(this.projectTitle);
            projectAdminInitialsText.setText(this.projectAdminInitials);
            // Get project admin initials
            this.projectAdminInitials = project.getProjectLeaderInitials();
            checkAdmin();
        }
    }

    // Check for admin / manager
    @FXML
    private void checkAdmin() {
        System.out.println("Checking for admin");
        viewTotalHours.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> !currentUser.equals(projectAdminInitials)
            )
        );
    }

    // Create Activity
    @FXML
    private void createActivity(ActionEvent event) {
        System.out.println("Create Activity");
        // Get project ID and make new activity
        String projectID = projectData.getProjectID();
        System.out.println("Project ID: " + projectID);
        Project updatedProject = projectService.createActivityForProject(
            projectID, 
            "Test1", 
            15, 
            43, 
            46, 
            2025, 
            2026
        );

        updateProjectActivityList(updatedProject);
    }

    // Open Activity
    @FXML
    private void openActivity(ActionEvent event) {
        System.out.println("Open Activity");
    }

    // See Time Summary only manager / project leader
    @FXML
    private void seeTimeSummary(ActionEvent event) {
        System.err.println("ONLY MANAGER / PROJECT LEADER");
        System.out.println("See Time Summary");
    }

    // Update Project Activity List
    private void updateProjectActivityList(Project updatedProject) {
        System.out.println("Update Project Activity List");
        this.projectData = updatedProject;
    }
}
