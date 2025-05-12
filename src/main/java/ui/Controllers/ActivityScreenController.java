// [Written by s246060] //

package ui.Controllers;

// Logic
import app.activity.AddTimeHandler;
import app.activity.EditActivityHandler;
import app.activity.AddMemberHandler;

// Domain
import domain.Activity;
import domain.Project;

// JavaFX
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

// Java
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityScreenController implements Initializable {

    @FXML private Label projectLabel;
    @FXML private Label activityLabel;
    @FXML private Label periodLabel;
    @FXML private Label budgetLabel;
    @FXML private Label loggedLabel;
    @FXML private Label membersLabel;

    @FXML private TableView<TimeEntry> timeLogTable;
    @FXML private TableColumn<TimeEntry, String> colMember;
    @FXML private TableColumn<TimeEntry, String> colDate;
    @FXML private TableColumn<TimeEntry, String> colHours;

    @FXML private ListView<String> memberList;

    @FXML private Button btnAddTime;
    @FXML private Button btnEditActivity;
    @FXML private Button btnAddMember;
    @FXML private Button btnRemoveMember;
    @FXML private Button btnBack;

    private Scene previousScene;
    private Runnable returnHandler;
    private Activity activity;

    private final ObservableList<TimeEntry> timeEntries = FXCollections.observableArrayList();
    private final ObservableList<String> members = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMember.setCellValueFactory(data -> data.getValue().userInitialsProperty());
        colDate.setCellValueFactory(data -> data.getValue().dateProperty());
        colHours.setCellValueFactory(data -> data.getValue().hoursProperty());

        timeLogTable.setItems(timeEntries);
        memberList.setItems(members);
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setReturnHandler(Runnable handler) {
        this.returnHandler = handler;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;

        Project project = activity.getProject();
        projectLabel.setText("Project: " + (project != null ? project.getProjectName() : "(unknown)"));
        activityLabel.setText("Activity: " + activity.getName());

        String period = "Week " + activity.getStartWeek() + " (" + activity.getStartYear() + ")"
                + " - Week " + activity.getEndWeek() + " (" + activity.getEndYear() + ")";
        periodLabel.setText("Period: " + period);

        budgetLabel.setText("Budgeted time: " + activity.getBudgetedTime() + " hours");
        loggedLabel.setText("Logged time: " + activity.getLoggedTime() + " hours");

        members.setAll(activity.getAssignedEmployees());
        membersLabel.setText("Assigned members: " + members.size());

        timeEntries.clear();
        for (Activity.TimeEntry entry : activity.getTimeLog()) {
            timeEntries.add(new TimeEntry(
                    entry.userInitialsProperty(),
                    entry.dateProperty(),
                    entry.hoursProperty()
            ));
        }
    }

    @FXML
    private void onBack() {
        if (returnHandler != null) returnHandler.run();
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(previousScene);
    }

    @FXML
    private void onAddTime() {
        double added = AddTimeHandler.handle(activity);
        if (added > 0) {
            loggedLabel.setText("Logged time: " + activity.getLoggedTime() + " hours");

            Activity.TimeEntry last = activity.getTimeLog().get(activity.getTimeLog().size() - 1);
            timeEntries.add(new TimeEntry(
                    last.userInitialsProperty(),
                    last.dateProperty(),
                    last.hoursProperty()
            ));
        }
    }

    @FXML
    private void onEditActivity() {
        EditActivityHandler.handle(activity);
        activityLabel.setText("Activity: " + activity.getName());
        budgetLabel.setText("Budgeted time: " + activity.getBudgetedTime() + " hours");
    }

    @FXML
    private void onAddMember() {
        AddMemberHandler.handle(activity);
        members.setAll(activity.getAssignedEmployees());
        membersLabel.setText("Assigned members: " + members.size());
    }

    @FXML
    private void onRemoveMember() {
        String selected = memberList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                activity.unassignEmployee(selected);
                members.remove(selected);
                membersLabel.setText("Assigned members: " + members.size());
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static class TimeEntry {
        private final StringProperty userInitials;
        private final StringProperty date;
        private final StringProperty hours;

        public TimeEntry(StringProperty userInitials, StringProperty date, StringProperty hours) {
            this.userInitials = userInitials;
            this.date = date;
            this.hours = hours;
        }

        public StringProperty userInitialsProperty() { return userInitials; }
        public StringProperty dateProperty() { return date; }
        public StringProperty hoursProperty() { return hours; }
    }
}