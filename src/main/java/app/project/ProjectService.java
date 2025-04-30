package app.project;

// Java utilities
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Folder imports
import domain.Project;
import persistence.Database;

import app.Main;

public class ProjectService {
    private static List<Project> projects;
    private static Database db;

    // Initiate the project / create a new project
    public ProjectService() {
        projects = new ArrayList<>();
        db = Main.getDatabase();
    }

    // Add a new project to the list of projects
    public static String addProject(String projectName) {
        Project project = new Project(projectName);
        projects.add(project);

        // Add project to the database
        db.addProject(project);
        return project.getProjectID();
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects);
    }

    // Find project by name
    public Optional<Project> findProjectByName(String projectName) {
        return projects.stream().filter(p -> p.getProjectName().equalsIgnoreCase(projectName)).findFirst();
    }

    // Find project by ID
    public static Optional<Project> findProjectByID(String projectID) {
        return projects.stream().filter(p -> p.getProjectID().equalsIgnoreCase(projectID)).findFirst();
    }

    // Open selected project
    public static void openProject(String projectID) {
        Optional<Project> project = findProjectByID(projectID);
        if (project.isPresent()) {
            // TODO: Open project
        }
    }
}