// [Written by s244706 and s246060] //

package ui.Controllers;

// Java
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Logic
import app.Main;
import app.employee.AuthValidation;
import app.project.ProjectService;
import app.report.CreateReport;

// Domain
import domain.Activity;
import domain.Project;
import domain.User;

// JavaFX
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Folder
import ui.BaseController;

public class ProjectScreenController extends BaseController {
    @FXML public Button chooseLeader;
    @FXML private Label projectNameText;
    @FXML private Label projectAdminInitialsText;
    @FXML private Button viewTotalHours;
    @FXML private ListView<Activity> activityListView;
    @FXML private Button openActivityButton;
    @FXML private Button backButton;

    private final ProjectService projectService = Main.getProjectService();
    private final CreateReport reportSystem = new CreateReport();

    private String projectTitle = "Project Name";
    private String projectAdminInitials = "Admin Initial";
    private String currentUser = "TestUser";
    private Project projectData;

    private Runnable returnHandler;

    @FXML
    private void initialize() {
        User user = AuthValidation.getCurrentUser();
        this.currentUser = user.getInitials();

        openActivityButton.disableProperty().bind(
                activityListView.getSelectionModel()
                        .selectedItemProperty()
                        .isNull());
    }

    public void setReturnHandler(Runnable handler) {
        this.returnHandler = handler;
    }

    public void setupUI(Optional<Project> projectData) {
        if (projectData.isPresent()) {
            Project project = projectData.get();
            this.projectTitle = "Project Name: " + project.getProjectName();
            this.projectAdminInitials = "Manager: " + project.getProjectLeaderInitials();

            updateProjectActivityList(projectData);

            projectNameText.setText(this.projectTitle);
            projectAdminInitialsText.setText(this.projectAdminInitials);
            this.projectAdminInitials = project.getProjectLeaderInitials();

            checkAdmin();
        }
    }

    @FXML
    private void checkAdmin() {
        viewTotalHours.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !currentUser.equals(projectAdminInitials)));
    }

    @FXML
    private void handleEditProject(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXML/ProjectCreationScreen.fxml"));
        Parent pane = loader.load();

        ProjectCreationScreenController controller = loader.getController();
        controller.setEditMode(projectData);

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(pane));
        dialog.setTitle("Edit Project");
        dialog.showAndWait();

        projectTitle = "Project Name: " + projectData.getProjectName();
        projectNameText.setText(projectTitle);
    }

    @FXML
    private void handleCreateActivity(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/FXML/activityCreationScreen.fxml"));
        Parent pane = loader.load();

        ActivityCreationScreenController creationCtrl = loader.getController();
        creationCtrl.setProjectService(projectService);
        creationCtrl.setProjectData(projectData);

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(pane));
        dialog.showAndWait();

        loadActivities();
    }

    @FXML
    private void seeTimeSummary(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXML/TimeSummaryScreen.fxml"));
            Parent root = loader.load();

            TimeSummaryScreenController controller = loader.getController();
            controller.setProject(projectData);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Project Time Summary");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Could not load time summary screen.");
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setHeaderText("Error");
        alert.showAndWait();
    }

    private void updateProjectActivityList(Optional<Project> updatedProjectData) {
        if (updatedProjectData != null && updatedProjectData.isPresent()) {
            this.projectData = updatedProjectData.get();
        } else {
            this.projectData = projectService.findProjectByID(projectData.getProjectID()).get();
        }

        loadActivities();
    }

    private void loadActivities() {
        activityListView.getItems().clear();
        activityListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ArrayList<Activity> activities = projectData.getActivities();
        activityListView.getItems().addAll(activities);
    }

    @FXML
    private void openActivity(ActionEvent event) {
        Activity selected = activityListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXML/SelectedActivity.fxml"));
            Parent activityRoot = loader.load();

            ActivityScreenController controller = loader.getController();
            controller.setActivity(selected);
            controller.setPreviousScene(openActivityButton.getScene());
            controller.setReturnHandler(this::loadActivities);

            Stage stage = (Stage) openActivityButton.getScene().getWindow();
            stage.setScene(new Scene(activityRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChooseLeader(ActionEvent event) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Choose Project Leader");
        dialog.setHeaderText("Select new project leader");
        dialog.setContentText("Members:");

        List<String> members = projectData.getMemberInitials();
        if (members.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "There are no members to choose from.").showAndWait();
            return;
        }

        dialog.getItems().addAll(members);
        dialog.setSelectedItem(projectData.getProjectLeaderInitials());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(initials -> {
            projectData.setProjectLeaderInitials(initials);
            projectAdminInitials = initials;
            projectAdminInitialsText.setText("Manager: " + initials);
            checkAdmin();
        });
    }

    @FXML
    public void handleBack(ActionEvent event) {
        if (returnHandler != null) {
            returnHandler.run();
        }
    }
}