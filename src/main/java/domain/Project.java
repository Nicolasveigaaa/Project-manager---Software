package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Project {
    private String projectID;
    private String projectName;
    private Employee projectManager;
    private Double totalHourAmount;
    private List<Activity> activities;

    private final String[] availableChars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
    private final String availableNumbs = "1234567890";

    private final Random random = new Random();

    public Project(String projectName) {
        this.projectID = createID();
        this.projectName = projectName;
        this.projectManager = null; // Projektlederen er ikke nødvendigvis udpeget fra starten.
        this.totalHourAmount = 0.0;
        this.activities = new ArrayList<>();
    }

    private String createID() {
        // Combine letters and numbers into one array
        String[] allChars = new String[availableChars.length + availableNumbs.length()];
        // Copy letters
        for (int i = 0; i < availableChars.length; i++) {
            allChars[i] = availableChars[i];
        }
        // Copy numbers
        for (int i = 0; i < availableNumbs.length(); i++) {
            allChars[availableChars.length + i] = String.valueOf(availableNumbs.charAt(i));
        }

        // Generate ID of 6 characters
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int randomIndex = random.nextInt(allChars.length);
            id.append(allChars[randomIndex]);
        }
        return id.toString();
    }

    // getters
    public String getProjectID() {
        return projectID;
    }  
    public String getProjectName() {
        return projectName;
    }
    public Employee getProjectManager() {
        return projectManager;
    }
    public Double getTotalHourAmount() {
        return totalHourAmount;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    // Changing
    public void setProjectManager(Employee projectManager) {
        this.projectManager = projectManager;
    }
    public void setTotalHourAmount(Double totalHourAmount) {
        this.totalHourAmount = totalHourAmount;
    }
}
