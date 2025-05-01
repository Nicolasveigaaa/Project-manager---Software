// [Written by s244706] //

package app.project;

// Java utilities
import java.util.List;
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
    private static List<Project> projects;

    // Initiate the project / create a new project
    public ProjectService() {
        db = Main.getDatabase();
    }

    // Create a new project and add it to the database
    public String addProject(String projectName) {
        // project constructor adds the user
        Project project = new Project(projectName);
        // Add project to the database
        db.addProject(project);
        return project.getProjectID();
    }

    public List<Project> getProjects() {
        // return projects directly from the database
        return db.getAllProjects();
    }

    public Optional<Project> findProjectByName(String projectName) {
        // query the database via stream
        return db.getAllProjects().stream()
                 .filter(p -> p.getProjectName().equalsIgnoreCase(projectName))
                 .findFirst();
    }

    // Find project by ID
    public static Optional<Project> findProjectByID(String projectID) {
        return projects.stream().filter(p -> p.getProjectID().equalsIgnoreCase(projectID)).findFirst();
    }

    // Open selected project
    public static Optional<Project> openProject(String projectID) {
        Optional<Project> project = findProjectByID(projectID);
        if (project.isPresent()) {
            System.out.println("Returning project data");
            return project;
        } else {
            System.out.println("Project not found");
            return Optional.empty();
        }
    }
    

    public Activity createActivityForProject(String projectID, String activityName, double budgetedTime, int startWeek, int startYear, int endWeek, int endYear) {
        Project project = findProjectByID(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found. Cannot create activity."));

        // create the Activity obect where constructor associates it with the project
        Activity newActivity = new Activity(project, activityName, budgetedTime, startWeek, startYear, endWeek, endYear);

        //add the Activity to the Project object 
        project.addActivity(newActivity);

        return newActivity;
    }

    public Collection<Activity> getActivitiesForProject(String projectID) {
        Project project = findProjectByID(projectID)
            .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found."));
 
        return project.getActivities(); // Delegate to the project object (returns unmodifiable collection)
    }

    // Project leader methods:

    public void setProjectLeader(String projectID, String leaderInitials) {
        Project project = findProjectByID(projectID)
            .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found."));
        User leader = db.getUser(leaderInitials.toLowerCase());
        if (leader == null) {
            throw new IllegalArgumentException("User with initials '" + leaderInitials + "' not found.");
        }
        project.setProjectLeaderInitials(leaderInitials); // Set leader on the project object
    }

    public void assignEmployeeToActivity(String projectID, String activityName, String employeeInitialsToAssign) {
        Project project = findProjectByID(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found."));

        Activity activity = project.getActivityByName(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name '" + activityName + "' not found in project '" + projectID + "'.");
        }

        User employee = db.getUser(employeeInitialsToAssign.toLowerCase());
        if (employee == null) {
            throw new IllegalArgumentException("Employee with initials '" + employeeInitialsToAssign + "' not found.");
        }

        // Delegate assignment and its specific checks (like already assigned) to Activity
        try {
              activity.assignEmployee(employee);
        } catch (IllegalArgumentException e) {
              throw new IllegalArgumentException("Could not assign employee: " + e.getMessage());
        }
    }

    // Time logging method:

    public void logTimeForActivity(String projectID, String activityName, double hours) {
        Project project = findProjectByID(projectID)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found."));

        Activity activity = project.getActivityByName(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name '" + activityName + "' not found in project '" + projectID + "'.");
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
                .orElseThrow(() -> new IllegalArgumentException("Project with ID '" + projectID + "' not found. Cannot generate report."));
    
        // Delegate the calculation to the CreateReport class
        return CreateReport.generateProjectTimeSummary(project);
    }

}