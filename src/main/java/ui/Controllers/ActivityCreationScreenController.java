// [Written by s244706 and s246060 ] //
package ui.Controllers;

// Logic imports
import app.project.ProjectService;

// Domain imports
import domain.Project;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Java
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class ActivityCreationScreenController {
    private ProjectService projectService;
    private Project projectData;

    @FXML private TextField activityNameField;
    @FXML private TextField budgetTime;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    public void setProjectService(ProjectService svc) {
        this.projectService = svc;
    }

    public void setProjectData(Project project) {
        this.projectData = project;
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        String name = activityNameField.getText().trim();
        String budget = budgetTime.getText().trim();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (name.isEmpty() || budget.isEmpty() || startDate == null || endDate == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Please fill in all fields: name, budget, start and end date.")
                    .showAndWait();
            return;
        }

        try {
            double budgetVal = Double.parseDouble(budget);

            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int startWeek = startDate.get(weekFields.weekOfWeekBasedYear());
            int startYear = startDate.getYear();
            int endWeek = endDate.get(weekFields.weekOfWeekBasedYear());
            int endYear = endDate.getYear();

            Project updatedProject = projectService.createActivityForProject(
                    projectData.getProjectID(),
                    name,
                    budgetVal,
                    startWeek,
                    endWeek,
                    startYear,
                    endYear
            );

            if (updatedProject == null) return;

            Stage stage = (Stage) activityNameField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING,
                    "Please enter a valid number for budget.")
                    .showAndWait();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) activityNameField.getScene().getWindow();
        stage.close();
    }

    public static void show() {
        try {
            Parent root = FXMLLoader.load(
                    ProjectCreationScreenController.class
                            .getResource("/ui/FXML/activityCreationScreen.fxml"));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Create New Activity");
            dialog.setScene(new Scene(root, 350, 500));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
