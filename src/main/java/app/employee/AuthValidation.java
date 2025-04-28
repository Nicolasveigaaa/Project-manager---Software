package app.employee;

import domain.Employee;
import persistence.Database;

public class AuthValidation {
    private final Database database;

    public AuthValidation(Database database) {
        this.database = database;
    }
    
    public boolean validateLogin(String initials, String password) {
        Employee user = database.getUser(initials.toLowerCase());
        return user != null && user.getPassword().equals(password);
    }
}