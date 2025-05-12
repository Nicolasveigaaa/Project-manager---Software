package app.activity;

import domain.Activity;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.function.Supplier;

import app.employee.AuthValidation;

// THIS USES JAVAFX
public class AddTimeHandler {
    private static Activity activity;

    // By default, we use the real JavaFX dialog:
    private static Supplier<Optional<String>> dialogSupplier = () -> {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Log Time");
        dialog.setHeaderText("Log time to activity: " + activity.getName());
        dialog.setContentText("Enter hours (e.g., 1.5):");
        return dialog.showAndWait();
    };

    /** Allows tests to override how we get the user’s input. */
    public static void setDialogSupplier(Supplier<Optional<String>> s) {
        dialogSupplier = s;
    }

    public static double handle(Activity activity) {
        activity = activity;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Log Time");
        dialog.setHeaderText("Log time to activity: " + activity.getName());
        dialog.setContentText("Enter hours (e.g., 1.5):");

        Optional<String> result = dialogSupplier.get();
        if (result.isPresent()) {
            String input = result.get();
            try {
                double hours = Double.parseDouble(input);

                String initials = AuthValidation.getCurrentUser().getInitials();
                if (!activity.isAssigned(initials)) {
                    showError("You are not assigned to this activity.");
                    return 0.0;
                }

                String today = java.time.LocalDate.now().toString();
                activity.addTimeEntry(initials, today, hours);
                return hours;

            } catch (NumberFormatException e) {
                showError("Please enter a valid number (e.g., 2.0).");
            } catch (IllegalArgumentException e) {
                showError("Error: " + e.getMessage());
            }
        }
        return 0.0;
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Cannot log time");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}