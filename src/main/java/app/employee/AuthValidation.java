package app.employee;

import domain.Employee;
import persistence.Database;

public class AuthValidation {
    private final Database database;
    private Employee currentUser;        // ← store the authenticated user

    public AuthValidation(Database database) {
        this.database = database;
    }

    /** Returns true _and_ sets currentUser on success */
    public boolean validateLogin(String initials, String password) {
        Employee user = database.getUser(initials.toLowerCase());
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    /** Once someone is logged in, let others ask “who is it?” */
    public Employee getCurrentUser() {
        return currentUser;
    }


}