package domain;

import java.util.ArrayList;

public class Employee {
    private final String initials;
    private final String password;

    public Employee(String initials, String password) {
        this.initials = initials.toLowerCase(); // lowercase to combat case sensitivity
        this.password = password;
    }

    public String getInitials() {
        return initials;
    }

    public String getPassword() {
        return password;
    }
}
