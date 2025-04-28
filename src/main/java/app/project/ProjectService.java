package app.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Project;

public class ProjectService {
    private final List<Project> projects;

    // Initiate the project / create a new project
    public ProjectService() {
        this.projects = new ArrayList<>();
    }

    public ProjectService(String projectName) {
        this();
        addProject(projectName);
    }

    // Add a new project to the list of projects
    public Project addProject(String projectName) {
        Project project = new Project(projectName);
        projects.add(project);
        return project;
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects);
    }

    // Find project by name
    public Optional<Project> findProjectByName(String projectName) {
        return projects.stream().filter(p -> p.getProjectName().equalsIgnoreCase(projectName)).findFirst();
    }

    // Find project by ID
    public Optional<Project> findProjectByID(String projectID) {
        return projects.stream().filter(p -> p.getProjectID().equalsIgnoreCase(projectID)).findFirst();
    }
}