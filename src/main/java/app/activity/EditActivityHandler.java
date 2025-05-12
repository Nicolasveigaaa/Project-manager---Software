// [Written by s246060]
package app.activity;

import domain.Activity;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class EditActivityHandler {
    public static void handle(Activity activity) {
        TextInputDialog nameDialog = new TextInputDialog(activity.getName());
        nameDialog.setTitle("Edit Activity");
        nameDialog.setHeaderText("Change activity name:");
        Optional<String> nameResult = nameDialog.showAndWait();

        nameResult.ifPresent(newName -> {
            if (!newName.isBlank()) {
                activity.setName(newName);
            }
        });

        TextInputDialog timeDialog = new TextInputDialog(String.valueOf(activity.getBudgetedTime()));
        timeDialog.setHeaderText("Set new budgeted time (in hours):");
        Optional<String> timeResult = timeDialog.showAndWait();

        timeResult.ifPresent(timeInput -> {
            try {
                double newBudget = Double.parseDouble(timeInput);
                activity.setBudgetedTime(newBudget);
            } catch (NumberFormatException e) {
                System.err.println("Invalid time input.");
            }
        });
    }
}