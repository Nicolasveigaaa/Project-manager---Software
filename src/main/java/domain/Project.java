// [Written by s244706 and s246060 additions] //

package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import app.employee.AuthValidation;

public class Project {
    private final String projectName;
    private final List<String> memberInitials;
    private final String projectID;
    private String projectLeaderInitials; // Added for project leader
    private final Map<String, Activity> activities = new HashMap<>();  // Map to store activities within this project, keyed by unique activity name

    public Project(String projectName, String projectID) {
        this.projectName     = projectName;
        this.memberInitials  = new ArrayList<>();
        this.projectID       = projectID;  //createID();
        this.projectLeaderInitials = null; // Leader not assigned initially
        this.addMember(AuthValidation.getCurrentUser().getInitials()); // Default add current user
    }

    public void setProjectLeaderInitials(String leaderInitials) {
        if (leaderInitials == null) {
            return;
        }
        // Add validation
        this.projectLeaderInitials = leaderInitials;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectLeaderInitials() { 
        return projectLeaderInitials; 
    }

    @Override
    public String toString() {
        return projectName + " (ID: " + projectID + ", Members: " + memberInitials.size() + ", Activities: " + activities.size() + ")";
    }

    // Add a member to the project (s246060)
    public void addMember(String initials) {
        if (!memberInitials.contains(initials)) { // Avoid duplicates
            memberInitials.add(initials);
        }
    }

    public List<String> getMemberInitials() {
        return memberInitials;
    }

    public String getProjectID() {
        return projectID;
    }

    // methods for activity management:

    public void addActivity(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Cannot add a null activity.");
        }
        // Ensure the activity being added points back to this project instance
        if (activity.getProject() != this) {
             throw new IllegalArgumentException("Activity belongs to a different project. Cannot add.");
        }
        // Check if an activity with the same name already exists
        if (this.activities.containsKey(activity.getName())) {
            throw new IllegalArgumentException("Activity with name '" + activity.getName() + "' already exists in this project.");
        }
        // Add the activity to the map
        this.activities.put(activity.getName(), activity);
    }

    public Activity getActivityByName(String name) {
        return this.activities.get(name); // Returns null if the key (name) is not found
    }

    public ArrayList<Activity> getActivities() {
        // Return all acitivities
        return new ArrayList<>(activities.values());
    }

}