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
    private Set<Employee> assignedEmployees;
    private double loggedTime; // in 0.5 hours precission

    // constructor
    public Activity(Project project, String name, double budgetedTime, int startWeek, int startYear, int endWeek, int endYear) {
        this.project = project;
        this.name = name;
        this.budgetedTime = budgetedTime;
        this.startWeek = startWeek;
        this.startYear = startYear;
        this.endWeek = endWeek;
        this.endYear = endYear;
        this.assignedEmployees = new HashSet<>();
        this.loggedTime = 0.0;
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
    public Set<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    // setters
    public void setName(String name) {
        this.name = name;
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

    public void assignEmployee(Employee employee) {
        if (assignedEmployees.contains(employee)) {
            throw new IllegalArgumentException("The employee is already assigned to the activity");
        }
        assignedEmployees.add(employee);
    }

    public void unassignEmployee(Employee employee) {
        if (!assignedEmployees.contains(employee)) {
            throw new IllegalArgumentException("The employee is not assigned to the activity");
        }
        assignedEmployees.remove(employee);
    }

    public void logTime(double hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("Logged time must be positive.");
        }
        this.loggedTime += hours;
    }

}

