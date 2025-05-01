package domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String initials;
    private final String password;
    private final String role;
    // maybe calculate totalHours elsewhere if meant for reporting
    private final Double totalHours;
    private List<TimeOff> timeOffList = new ArrayList<>(); // keeps track of fixed activities

    public User(String initials, String password, String role) {
        this.initials = initials.toLowerCase(); // lowercase to combat case sensitivity
        this.password = password;
        this.role = role;
        this.totalHours = 0.0;
    }

    public String getInitials() { return initials; }
    public String getPassword() { return password; }
    public Double getTotalHours() { return totalHours; }
    public String getRole() { return role; }
    public List<TimeOff> getTimeOffList() { return new ArrayList<>(timeOffList); }

    // method for adding fixed activities
    public void addTimeOff(TimeOff timeOff) {
        // Optional: Add checks for overlapping time off if needed
        this.timeOffList.add(timeOff);
        // Optional: Add removeTimeOff if needed
    }
}
