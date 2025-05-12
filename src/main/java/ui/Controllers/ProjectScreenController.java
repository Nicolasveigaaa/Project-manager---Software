// [Written by s244706 and s246060] //

package ui.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import app.Main;
import app.employee.AuthValidation;
import app.project.ProjectService;
import app.report.CreateReport;
import domain.Activity;
import domain.Project;
import domain.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.BaseController;

public class ProjectScreenController extends BaseController {
    // ADD FXML
    @FXML
    private Label projectNameText;
    @FXML
    private Label projectAdminInitialsText;
    @FXML
    private Button viewTotalHours;
    @FXML
    private ListView<Activity> activityListView;
    @FXML
    private Button openActivityButton;

    // Require the project service from the main / avoiding using static references,
    // hehe, smart right.
    private final ProjectService projectService = Main.getProjectService();
    private final CreateReport reportSystem = new CreateReport();

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

        // Disable "Open" until something is selected:
        openActivityButton.disableProperty().bind(
                activityListView.getSelectionModel()
                        .selectedItemProperty()
                        .isNull());
    }

    public void setupUI(Optional<Project> projectData) {
        if (projectData.isPresent()) {
            // Get project name and admin initials
            Project project = projectData.get();
            this.projectTitle = "Project Name: " + project.getProjectName();
            this.projectAdminInitials = "Manager: " + project.getProjectLeaderInitials();

            // Setup activity list UI
            updateProjectActivityList(projectData);

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
                        () -> !currentUser.equals(projectAdminInitials)));
    }

    // Add Member
    @FXML
    private void handleAddMemberProject(ActionEvent event) throws IOException {
        System.out.println("Add Member");
        // projectData is guaranteed non-null here
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/FXML/AddMemberController.fxml"));
        Parent pane = loader.load();

        // grab its controller instance
        AddMemberController creationCtrl = loader.getController();
        creationCtrl.addUserButton(projectData);

        // show as modal
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(pane));
        dialog.showAndWait();
    }

    // Create Activity
    @FXML
    private void handleCreateActivity(ActionEvent event) throws IOException {
        // projectData is guaranteed non-null here
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/FXML/activityCreationScreen.fxml"));
        Parent pane = loader.load();

        // grab its controller instance
        ActivityCreationScreenController creationCtrl = loader.getController();
        creationCtrl.setProjectService(projectService);
        creationCtrl.setProjectData(projectData);

        // show as modal
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(pane));
        dialog.showAndWait();

        // refresh the list after the dialog closes
        loadActivities();
    }

    // See Time Summary only manager / project leader
    @FXML
    private void seeTimeSummary(ActionEvent event) {
        CreateReport.generateProjectTimeSummary(projectData);
        System.err.println("ONLY MANAGER / PROJECT LEADER");
        System.out.println("See Time Summary");
    }

    // Update Project Activity List
    private void updateProjectActivityList(Optional<Project> updatedProjectData) {
        System.out.println("Update Project Activity List");

        if (updatedProjectData != null && updatedProjectData.isPresent()) {
            this.projectData = updatedProjectData.get();
        } else {
            this.projectData = projectService.findProjectByID(projectData.getProjectID()).get();
        }

        loadActivities();
    }

    // Load Activities
    private void loadActivities() {
        // Clear current projects from screen
        activityListView.getItems().clear();

        // enforce single‐selection
        activityListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ArrayList<Activity> activities = projectData.getActivities();

        activityListView.getItems().addAll(activities);
    }

    // Activity Button
    @FXML
    private void openActivity(ActionEvent event) {
        Activity selected = activityListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            // Load new FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXML/SelectedActivity.fxml"));
            Parent activityRoot = loader.load();

            // Pass selected activity to the controller
            ActivityScreenController controller = loader.getController();
            controller.setActivity(selected);
            controller.setPreviousScene(openActivityButton.getScene()); // Save current scene for "Back"

            // Replace scene in current stage
            Stage stage = (Stage) openActivityButton.getScene().getWindow();
            stage.setScene(new Scene(activityRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
