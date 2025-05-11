// [Written by s244706] //

package app.project;

// Java utilities
import java.util.Optional;
import java.util.Collection;
import java.util.Map;

// Folder imports
import domain.Activity;
import domain.User;
import domain.Project;
import persistence.Database;
import app.report.CreateReport;

import app.Main;

// Service layer class for handling project and activity related operations
public class ProjectService {
    private static Database db;

    // Initiate the project / create a new project
    public ProjectService() {
        db = Main.getDatabase();
    }

    // Create a new project and add it to the database
    public static String addProject(String projectName) {
        if (projectName == null || projectName.isBlank()) {
            return null;
        }
        // project constructor adds the user
        
        Project project = new Project(projectName, db.getNextProjectID());
        // Add project to the database
        db.addProject(project);
        return project.getProjectID();
    }

    // public List<Project> getProjects() {
    // // return projects directly from the database
    // return db.getAllProjects();
    // }

    public Optional<Project> findProjectByName(String projectName) {
        // query the database via stream
        return db.getAllProjects().stream()
                .filter(p -> p.getProjectName().equalsIgnoreCase(projectName))
                .findFirst();
    }

    // Find project by ID
    public Optional<Project> findProjectByID(String projectID) {
        return db.getProject(projectID);
    }

    // Open selected project
    public Optional<Project> openProject(String projectID) {
        Optional<Project> project = findProjectByID(projectID);
        if (project.isPresent()) {
            System.out.println("Returning project data");
            return project;
        } else {
            System.out.println("Project not found");
            return Optional.empty();
        }
    }

    public Project createActivityForProject(String projectID, String activityName, double budgetedTime, int startWeek, int endWeek, int startYear, int endYear) {
        Project project = findProjectByID(projectID)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Project with ID '" + projectID + "' not found. Cannot create activity."));

        // Check if the activity already exists
        if (project.getActivityByName(activityName) != null || activityName.isBlank()) {
            new IllegalArgumentException(
                        "Activity with name '" + activityName + "' is blank. Cannot create activity.");
            return null;
        }

        // create the Activity obect where constructor associates it with the project
        Activity newActivity = new Activity(project, activityName, budgetedTime, startWeek, startYear, endWeek,
                endYear);

        // add the Activity to the Project object
        project.addActivity(newActivity);

        return project;
    }

    public Collection<Activity> getActivitiesForProject(String projectID) {
        Project project = findProjectByID(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found."));

        return project.getActivities(); // Delegate to the project object (returns unmodifiable collection)
    }

    // Project leader methods:
    public void setProjectLeader(String projectID, String leaderInitials) {
        // Precondition asserts:
        assert projectID != null : "Precondition violated: projectID cannot be null.";
        assert leaderInitials != null : "Precondition violated: leaderInitials cannot be null.";
        assert db.getProject(projectID).isPresent() : "Precondition Violated: Project with ID '" + projectID + "' must exist.";
        assert db.getUser(leaderInitials.toLowerCase()) != null : "Precondition Violated: User with initials '" + leaderInitials + "' must exist.";

        Project project = findProjectByID(projectID)                                                            // 1
            .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found.")); // 1a
        

        User leader = db.getUser(leaderInitials.toLowerCase());                                                 // 2
        if (leader == null) {                                                                                   // 3
            throw new IllegalArgumentException("User with initials '" + leaderInitials + "' not found.");       // 3a
        }
        
        project.setProjectLeaderInitials(leaderInitials); // Set leader on the project object                   // 4

        // Postcondition assert:
        assert project.getProjectLeaderInitials().equals(leaderInitials) : "Postcondition violated: Project leader was not set correctly.";
    }                                                                                                   

    public void assignEmployeeToActivity(String projectID, String activityName, String userInitials) {

        // Preconditions ( asserted )
        // assert projectID != null : " projectID_must_not_be_null ";
        // assert activityName != null : " activityName_must_not_be_null ";
        // assert userInitials != null : " userInitials_must_not_be_null ";

        // Step 1 2 : find project or throw
        Project project = findProjectByID(projectID)                                                                    // 1
                .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found."));     // 2

        // Step 3 5 : find activity or throw
        Activity activity = project.getActivityByName(activityName);                                                    // 3

        //assert activity != null : " Activity_'" + activityName + " '_not_found "; // corresponds to Q1 

        if (activity == null) {                                                                                         // 4
            throw new IllegalArgumentException(
                    "Activity with name: " + activityName + "not found.");             // 5    
        }

        // Step 6 7 : check for null user or throw
        System.out.println("User initials: " + userInitials); // Debugging line
        if (userInitials == null || userInitials.isBlank()) {                                                                                     // 6
            throw new IllegalArgumentException("Cannot assign null user.");                                           // 7
        }

        // Step 8 9 : delegate to Activity . assignEmployee , catching
        try {
            activity.assignEmployee(userInitials);                                                                      // 8     
        } catch (IllegalArgumentException e) {
            //assert !activity.isAssigned(userInitials) : " User_is_already_assigned "; // corresponds to Q2

            throw new IllegalArgumentException("Could not assign employee: " + e.getMessage());                         // 9
        }

        // Postcondition : user must now be assigned
        //assert activity.isAssigned(userInitials) : " User_'" + userInitials + " '␣ should_be_assigned "; // corresponds to Q3
    }

    // Time logging method:
    public void logTimeForActivity(String projectID, String activityName, double hours) {
        Project project = findProjectByID(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found."));

        Activity activity = project.getActivityByName(activityName);
        if (activity == null) {
            throw new IllegalArgumentException(
                    "Activity with name '" + activityName + "' not found in project '" + projectID + "'.");
        }

        // Delegate logging and its specific checks (like hours > 0) to Activity
        // The try-catch here is mostly if logTime itself throws, which it does
        try {
            activity.logTime(hours);
        } catch (IllegalArgumentException e) {
            // Re-throw the specific message from Activity's validation
            throw new IllegalArgumentException("Could not log time: " + e.getMessage());
        }
    }

    // Method to generate the time summary report for a project
    public Map<String, Double> getProjectTimeSummary(String projectID) {
        Project project = findProjectByID(projectID)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Project with ID '" + projectID + "' not found. Cannot generate report."));

        // Delegate the calculation to the CreateReport class
        return CreateReport.generateProjectTimeSummary(project);
    }

}