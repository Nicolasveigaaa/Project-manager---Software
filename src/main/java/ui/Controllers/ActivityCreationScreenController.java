// [Written by s244706] //
package ui.Controllers;

import java.io.IOException;
import app.project.ProjectService;
import domain.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActivityCreationScreenController {
    private ProjectService projectService;
    private Project projectData;

    @FXML
    private TextField activityNameField;
    @FXML
    private TextField budgetTime;

    public void setProjectService(ProjectService svc) {
        this.projectService = svc;
    }

    public void setProjectData(Project project) {
        this.projectData = project;
    }

    // Validates inputs and creates a new project in the persistence database
    @FXML
    private void handleCreate(ActionEvent event) {
        String name = activityNameField.getText().trim();
        String budget = budgetTime.getText().trim();

        if (name.isEmpty() || budget.isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Please enter an activity name and budgeted time.")
                    .showAndWait();
            return;
        }

        // Check if the budget is a valid number
        try {
            Double.parseDouble(budget);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING,
                    "Please enter a valid budgeted time.")
                    .showAndWait();
            return;
        }

        Project updatedProject = projectService.createActivityForProject(
                projectData.getProjectID(),
                name,
                Double.parseDouble(budget),
                43,
                46,
                2025,
                2026);

        if (updatedProject == null) {
            return;
        }

        // Close the dialog window
        Stage stage = (Stage) activityNameField.getScene().getWindow();
        stage.close();
    }

    // Closes modal dialog without creating a project
    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) activityNameField.getScene().getWindow();
        stage.close();
    }

    // Shows screen as modal instead of screen
    public static void show() {
        try {
            Parent root = FXMLLoader.load(
                    ProjectCreationScreenController.class
                            .getResource("/ui/FXML/activityCreationScreen.fxml"));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Create New Project");
            dialog.setScene(new Scene(root, 300, 350));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
