// [Written by s246060]
package app.activity;

import domain.Activity;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AddTimeHandler {

    public static void handle(Activity activity) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Log Time");
        dialog.setHeaderText("Log time to activity: " + activity.getName());
        dialog.setContentText("Enter hours (e.g., 1.5):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                double hours = Double.parseDouble(input);
                activity.logTime(hours);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format.");
            } catch (IllegalArgumentException e) {
                System.err.println("Error logging time: " + e.getMessage());
            }
        });
    }
}