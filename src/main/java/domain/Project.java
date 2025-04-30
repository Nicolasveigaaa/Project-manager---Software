package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.employee.AuthValidation;

public class Project {
    private final String projectName;
    private final List<String> memberInitials;
    private final String projectID;
    private String projectLeaderInitials; // Added for project leader

    public Project(String projectName) {
        this.projectName     = projectName;
        this.memberInitials  = new ArrayList<>();
        this.projectID       = createID();
        this.projectLeaderInitials = null; // Leader not assigned initially
        // Default add current user
        this.addMember(AuthValidation.getCurrentUser().getInitials());
    }


    public String getProjectName() {
        return projectName;
    }

    public String getProjectLeaderInitials() { 
        return projectLeaderInitials; 
    }

    @Override
    public String toString() {
        return projectName + " (" + memberInitials.size() + " members)";
    }

    public void addMember(String initials) {
        if (!memberInitials.contains(initials)) { // Avoid duplicates
            memberInitials.add(initials);
        }
    }

    public List<String> getMemberInitials() {
        return memberInitials;
    }

    // VINCENT ID TING
    private final String[] availableChars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
    private final String availableNumbs = "1234567890";

    private final Random random = new Random();

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
        return "2025-"+id.toString();
    }

    public String getProjectID() {
        return projectID;
    }
}