package domain;

import java.util.Random;
import java.util.Scanner;

public class Project {
    private String projectID;
    private String projectName;
    private String projectManager;
    private Double totalHourAmount;

    private final String[] availableChars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
    private final String availableNumbs = "1234567890";

    private final Random random = new Random();

    public Project(String projectName) {
        this.projectID = createID();
        this.projectName = projectName;
        this.projectManager = "Unassigned";
        this.totalHourAmount = 0.0;
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

    public String getProjectID() {
        return projectID;
    }  
    public String getProjectName() {
        return projectName;
    }
    public String getProjectManager() {
        return projectManager;
    }
    public Double getTotalHourAmount() {
        return totalHourAmount;
    }

    // Changing
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }
    public void setTotalHourAmount(Double totalHourAmount) {
        this.totalHourAmount = totalHourAmount;
    }
}
