// [Written by s246060 ]

package app.activity;

import domain.Activity;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class EditActivityHandler {
    public static void handle(Activity activity) {
        // Edit Name
        TextInputDialog nameDialog = new TextInputDialog(activity.getName());
        nameDialog.setTitle("Edit Activity");
        nameDialog.setHeaderText("Change activity name:");
        Optional<String> nameResult = nameDialog.showAndWait();
        nameResult.ifPresent(newName -> {
            if (!newName.isBlank()) {
                activity.setName(newName);
            }
        });

        // Edit Budget
        TextInputDialog timeDialog = new TextInputDialog(String.valueOf(activity.getBudgetedTime()));
        timeDialog.setHeaderText("Set new budgeted time (in hours):");
        Optional<String> timeResult = timeDialog.showAndWait();
        timeResult.ifPresent(timeInput -> {
            try {
                double newBudget = Double.parseDouble(timeInput);
                if (newBudget < 0) throw new NumberFormatException();
                activity.setBudgetedTime(newBudget);
            } catch (NumberFormatException e) {
                showError("Invalid budget input. Please enter a positive number.");
            }
        });

        // Edit Start Week
        TextInputDialog startWeekDialog = new TextInputDialog(String.valueOf(activity.getStartWeek()));
        startWeekDialog.setHeaderText("Start week (e.g., 12):");
        Optional<String> startWeekResult = startWeekDialog.showAndWait();
        int startWeek = activity.getStartWeek();
        if (startWeekResult.isPresent()) {
            try {
                startWeek = Integer.parseInt(startWeekResult.get());
            } catch (NumberFormatException e) {
                showError("Invalid start week.");
            }
        }

        // Edit Start Year
        TextInputDialog startYearDialog = new TextInputDialog(String.valueOf(activity.getStartYear()));
        startYearDialog.setHeaderText("Start year (e.g., 2025):");
        Optional<String> startYearResult = startYearDialog.showAndWait();
        int startYear = activity.getStartYear();
        if (startYearResult.isPresent()) {
            try {
                startYear = Integer.parseInt(startYearResult.get());
            } catch (NumberFormatException e) {
                showError("Invalid start year.");
            }
        }

        // Edit End Week
        TextInputDialog endWeekDialog = new TextInputDialog(String.valueOf(activity.getEndWeek()));
        endWeekDialog.setHeaderText("End week (e.g., 20):");
        Optional<String> endWeekResult = endWeekDialog.showAndWait();
        int endWeek = activity.getEndWeek();
        if (endWeekResult.isPresent()) {
            try {
                endWeek = Integer.parseInt(endWeekResult.get());
            } catch (NumberFormatException e) {
                showError("Invalid end week.");
            }
        }

        // Edit End Year
        TextInputDialog endYearDialog = new TextInputDialog(String.valueOf(activity.getEndYear()));
        endYearDialog.setHeaderText("End year (e.g., 2025):");
        Optional<String> endYearResult = endYearDialog.showAndWait();
        int endYear = activity.getEndYear();
        if (endYearResult.isPresent()) {
            try {
                endYear = Integer.parseInt(endYearResult.get());
            } catch (NumberFormatException e) {
                showError("Invalid end year.");
            }
        }

        // Apply if valid
        activity.setStartWeekYear(startWeek, startYear);
        activity.setEndWeekYear(endWeek, endYear);
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Could not update activity");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}