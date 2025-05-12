// [Written by s246060 ]

package ui.Controllers;

import domain.Activity;
import domain.Project;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class TimeSummaryScreenController implements Initializable {

    @FXML private TableView<UserEntry> summaryTable;
    @FXML private TableColumn<UserEntry, String> colUser;
    @FXML private TableColumn<UserEntry, String> colHours;

    @FXML private Label totalHoursLabel;
    @FXML private Label totalBudgetLabel;
    @FXML private Label differenceLabel;

    @FXML private Button closeButton;

    private Project project;

    public void setProject(Project project) {
        this.project = project;
        generateSummary();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUser.setCellValueFactory(data -> data.getValue().userProperty());
        colHours.setCellValueFactory(data -> data.getValue().hoursProperty());

        closeButton.setOnAction(e -> ((Stage) closeButton.getScene().getWindow()).close());
    }

    private void generateSummary() {
        if (project == null) return;

        Map<String, Double> userHours = new HashMap<>();
        double totalBudget = 0.0;

        for (Activity activity : project.getActivities()) {
            totalBudget += activity.getBudgetedTime();
            for (Activity.TimeEntry entry : activity.getTimeLog()) {
                userHours.put(
                        entry.getUserInitials(),
                        userHours.getOrDefault(entry.getUserInitials(), 0.0) + entry.getHours()
                );
            }
        }

        double totalLogged = userHours.values().stream().mapToDouble(Double::doubleValue).sum();
        double difference = totalLogged - totalBudget;

        List<UserEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> e : userHours.entrySet()) {
            entries.add(new UserEntry(e.getKey(), e.getValue()));
        }

        summaryTable.setItems(FXCollections.observableArrayList(entries));
        totalHoursLabel.setText("Total hours spent: " + totalLogged);
        totalBudgetLabel.setText("Total budgeted hours: " + totalBudget);
        differenceLabel.setText("Difference: " + Math.abs(difference) + " hours " +
                (difference >= 0 ? "overworked" : "under budget"));
    }

    public static class UserEntry {
        private final StringProperty user;
        private final StringProperty hours;

        public UserEntry(String user, double hours) {
            this.user = new SimpleStringProperty(user);
            this.hours = new SimpleStringProperty(String.format("%.1f", hours));
        }

        public StringProperty userProperty() { return user; }
        public StringProperty hoursProperty() { return hours; }
    }
}