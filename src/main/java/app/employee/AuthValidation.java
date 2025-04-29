package app.employee;

// Folder imports
import domain.Employee;
import persistence.Database;

public class AuthValidation {
    private final Database database;
    private Employee currentUser;

    // Constructor to initialize the database
    public AuthValidation(Database database) {
        this.database = database;
    }

    // Validates user login is in the persistence database
    public boolean validateLogin(String initials, String password) {
        Employee user = database.getUser(initials.toLowerCase());
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    // Checks who the current user is
    public Employee getCurrentUser() {
        return currentUser;
    }


}