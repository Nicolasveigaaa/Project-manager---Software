// Jacob Knudsen (s224372 and s246060)
package domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

public class Activity {
    private String name;
    private double budgetedTime;
    private int startWeek;
    private int startYear;
    private int endWeek;
    private int endYear;
    private Project project;
    private final Set<String> assignedUsers;
    private double loggedTime;

    private final List<TimeEntry> timeLog = new ArrayList<>();

    // ----------- Constructors ----------- //
    public Activity(Project project, String name, double budgetedTime, int startWeek, int startYear, int endWeek, int endYear) {
        if (project == null) throw new IllegalArgumentException("Activity must belong to a project.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Activity name cannot be empty.");
        if (budgetedTime < 0) throw new IllegalArgumentException("Budgeted time cannot be negative.");

        this.project = project;
        setName(name);
        setBudgetedTime(budgetedTime);
        setStartWeekYear(startWeek, startYear);
        setEndWeekYear(endWeek, endYear);
        this.assignedUsers = new HashSet<>();
        this.loggedTime = 0.0;
    }

    public Activity(String name, Project project) {
        this(project, name, 0.0, 1, 2025, 2, 2025); // default placeholder values
    }

    // ----------- Getters ----------- //
    public String getName() {
        return name;
    }

    public double getBudgetedTime() {
        return budgetedTime;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public int getEndYear() {
        return endYear;
    }

    public Project getProject() {
        return project;
    }

    public double getLoggedTime() {
        return loggedTime;
    }

    public Set<String> getAssignedEmployees() {
        return assignedUsers;
    }

    public List<TimeEntry> getTimeLog() {
        return new ArrayList<>(timeLog);
    }

    // ----------- Setters ----------- //
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Activity name cannot be empty.");
        this.name = name.trim();
    }

    public void setBudgetedTime(double budgetedTime) {
        if (budgetedTime < 0)
            throw new IllegalArgumentException("Budgeted time cannot be negative.");
        this.budgetedTime = budgetedTime;
    }

    public void setStartWeekYear(int startWeek, int startYear) {
        this.startWeek = startWeek;
        this.startYear = startYear;
    }

    public void setEndWeekYear(int endWeek, int endYear) {
        this.endWeek = endWeek;
        this.endYear = endYear;
    }

    public void setLoggedTime(double loggedTime) {
        if (loggedTime < 0)
            throw new IllegalArgumentException("Logged time cannot be negative.");
        this.loggedTime = loggedTime;
    }

    // ----------- Assignment ----------- //
    public void assignEmployee(String initials) {
        if (initials == null || initials.isBlank())
            throw new IllegalArgumentException("Cannot assign null user.");
        if (!assignedUsers.add(initials))
            throw new IllegalArgumentException("Employee already assigned.");
    }

    public void unassignEmployee(String initials) {
        if (initials == null || initials.isBlank())
            throw new IllegalArgumentException("Cannot unassign null user.");
        if (!assignedUsers.remove(initials))
            throw new IllegalArgumentException("The employee is not assigned to the activity");
    }

    public boolean isAssigned(String initials) {
        if (initials == null || initials.isBlank())
            throw new IllegalArgumentException("Initials cannot be null or blank.");
        return assignedUsers.contains(initials);
    }

    // ----------- Logging ----------- //
    public void logTime(double hours) {
        if (hours <= 0)
            throw new IllegalArgumentException("Logged time must be positive.");
        this.loggedTime += hours;
    }

    public void addTimeEntry(String initials, String date, double hours) {
        if (initials == null || initials.isBlank()) throw new IllegalArgumentException("Initials required.");
        if (date == null || date.isBlank()) throw new IllegalArgumentException("Date required.");
        if (hours <= 0) throw new IllegalArgumentException("Hours must be > 0");

        this.loggedTime += hours;
        this.timeLog.add(new TimeEntry(initials, date, hours));
    }

    // ----------- Utility ----------- //
    public boolean isActiveIn(int week, int year) {
        if (year < startYear || year > endYear) return false;
        if (startYear == endYear) return week >= startWeek && week <= endWeek;
        if (year == startYear) return week >= startWeek;
        if (year == endYear) return week <= endWeek;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s (Budget: %.1f, Logged: %.1f, Assigned: %d)",
                this.name, this.budgetedTime, this.loggedTime, this.assignedUsers.size());
    }

    // ----------- Inner Class ----------- //
    public static class TimeEntry {
        private final StringProperty userInitials;
        private final StringProperty date;
        private final StringProperty hours;

        public TimeEntry(String userInitials, String date, double hours) {
            this.userInitials = new SimpleStringProperty(userInitials);
            this.date = new SimpleStringProperty(date);
            this.hours = new SimpleStringProperty(String.valueOf(hours));
        }

        public String getUserInitials() {
            return userInitials.get();
        }

        public String getDate() {
            return date.get();
        }

        public double getHours() {
            return Double.parseDouble(hours.get());
        }

        public StringProperty userInitialsProperty() {
            return userInitials;
        }

        public StringProperty dateProperty() {
            return date;
        }

        public StringProperty hoursProperty() {
            return hours;
        }
    }
}