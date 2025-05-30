// [Written by s246060]

package app.employee;

// Folder imports
import domain.User;
import persistence.Database;

public class AuthValidation {
    private final Database database;
    private static User currentUser;

    // Constructor to initialize the database
    public AuthValidation(Database database) {
        this.database = database;
    }

    // Validates user login is in the persistence database
    public boolean validateLogin(String initials) {
        User user = database.getUser(initials.toLowerCase());
        if (user != null) {
            currentUser = user;
            return true;
        } else {
            currentUser = null;
        }
        return false;
    }

    // Checks who the current user is
    public static User getCurrentUser() {
        return currentUser;
    }
}