// [Written by s246060]
package app.activity;

import domain.Activity;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AddMemberHandler {

    public static void handle(Activity activity) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Assign Employee");
        dialog.setHeaderText("Assign employee to: " + activity.getName());
        dialog.setContentText("Enter user initials (e.g., huba):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(initials -> {
            try {
                activity.assignEmployee(initials.trim());
            } catch (IllegalArgumentException e) {
                System.err.println("Assignment failed: " + e.getMessage());
            }
        });
    }
}