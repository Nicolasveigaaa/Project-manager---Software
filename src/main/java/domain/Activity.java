// Jacob Knudsen (s224372)
package domain;


import java.util.HashSet;
import java.util.Set;

public class Activity {
    private String name; // unique name within the project (e.g., "kravspecifikation", "design")
    private Double budgetedTime; // in 1 hour precission
    private int startWeek; 
    private int startYear; 
    private int endWeek;   
    private int endYear;  
    private Project project; // reference to the parent project
    private Set<String> assignedUsers;
    private double loggedTime; // in 0.5 hours precission

    // constructor
    public Activity(Project project, String name, double budgetedTime, int startWeek, int startYear, int endWeek, int endYear) {
        if (project == null) throw new IllegalArgumentException("Activity must belong to a project.");
        if (name == null || name.trim().isEmpty() || name.isBlank()) throw new IllegalArgumentException("Activity name cannot be empty.");
        if (budgetedTime < 0) throw new IllegalArgumentException("Budgeted time cannot be negative.");
        this.project = project;
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.startYear = startYear;
        this.endWeek = endWeek;
        this.endYear = endYear;
        this.assignedUsers = new HashSet<>();
        this.loggedTime = 0.0;
    }

    public Activity(String activityName, Project project2) {
        //TODO Auto-generated constructor stub
    }

    // getters
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

    public boolean isAssigned(String initials) {
        if (initials == null) throw new IllegalArgumentException("Cannot check assignment for null user.");
        return assignedUsers.contains(initials);
    }

    // setters
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Activity name cannot be empty.");
        this.name = name.trim();
    }

    public void setBudgetedTime(double budgetedTime) {
        if (budgetedTime < 0) {
            throw new IllegalArgumentException("Budgeted time cannot be negative.");
        }
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

    public void assignEmployee(String initials) {
        if (initials == null) throw new IllegalArgumentException("Cannot assign null user.");
        if (assignedUsers.contains(initials)) throw new IllegalArgumentException("The employee is already assigned to the activity");
        assignedUsers.add(initials);
    }

    public void unassignEmployee(String initials) {
        if (initials == null) throw new IllegalArgumentException("Cannot unassign null user.");
        if (!assignedUsers.contains(initials)) throw new IllegalArgumentException("The employee is not assigned to the activity");

        System.out.println("Unassigning " + initials + " from activity " + this.name);
        for (String user : assignedUsers) {
            System.out.println("Assigned user: " + user);
        }
        System.out.println("Is " + initials + " assigned? " + assignedUsers.contains(initials));
        assignedUsers.remove(initials);
    }

    public void logTime(double hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("Logged time must be positive.");
        }

        this.loggedTime += hours;
    }

    // check if a specific week falls within the activitys duration
    public boolean isActiveIn(int week, int year) {
        if (year < startYear || year > endYear) return false;
        if (year > startYear && year < endYear) return true;
        if (startYear == endYear) return week >= startWeek && week <= endWeek;
        if (year == startYear) return week >= startWeek;
        // if (year == endYear)
        return week <= endWeek;
    }

    @Override
    public String toString() {
        // Shows Name, Budgeted Time, Logged Time, and number of Assigned Users
        return String.format("%s (Budget: %.1f, Logged: %.1f, Assigned: %d)",
                             this.name,
                             this.budgetedTime,
                             this.loggedTime,
                             this.assignedUsers.size());
    }

}

