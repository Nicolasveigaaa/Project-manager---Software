// [Written by s244706] //

package ui.Controllers;

import java.util.Optional;

import app.Main;
import app.project.ProjectService;
import domain.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProjectScreenController {
    // ADD FXML
    @FXML
    private Label projectNameText;
    @FXML
    private Label projectAdminInitialsText;

    // Require the project service from the main / avoiding using static references, hehe, smart right.
    private final ProjectService projectService = Main.getProjectService();

    private String projectTitle = "Project Name";
    private String projectAdminInitials = "Admin Initial";
    private Project projectData;

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
        }
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
