package domain;

public class Employee {
    private final String initials;
    private final String password;
    private final String role;
    private final Double totalHours;

    public Employee(String initials, String password, String role) {
        this.initials = initials.toLowerCase(); // lowercase to combat case sensitivity
        this.password = password;
        this.role = role;
        this.totalHours = 0.0;
    }

    public String getInitials() {
        return initials;
    }

    public String getPassword() {
        return password;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public String getRole() {
        return role;
    }
}
