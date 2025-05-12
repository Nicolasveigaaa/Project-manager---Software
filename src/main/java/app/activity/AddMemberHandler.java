// [Written by s246060]
package app.activity;

import domain.Activity;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import persistence.Database;

import java.util.List;
import java.util.Optional;

public class AddMemberHandler {

    private static final Database db = new Database();

    public static void handle(Activity activity) {
        List<String> allUsers = db.getAllUserInitials();

        allUsers.removeAll(activity.getAssignedEmployees());

        if (allUsers.isEmpty()) {
            showError("No available users to assign.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(allUsers.get(0), allUsers);
        dialog.setTitle("Add Member");
        dialog.setHeaderText("Select a user to add to the activity");
        dialog.setContentText("Available users:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(initials -> {
            try {
                activity.assignEmployee(initials);
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        });
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Add Member Failed");
        alert.setHeaderText("Could not add member");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}