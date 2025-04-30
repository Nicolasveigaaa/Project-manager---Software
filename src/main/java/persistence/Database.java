package persistence;

// Folder imports
import domain.User;
import domain.Project;
import domain.Activity;
import domain.TimeOff;

// Java imports
import java.util.*;
import java.util.stream.Collectors;

public class Database {
    private final Map<String, User> allowedUsers = new HashMap<>();
    private final static Map<String, Project>  projects = new HashMap<>();
    private final Map<String, List<Activity>> projectActivities = new HashMap<>();

    // Constructor
    public Database() {
        // Hardcoded users — just add manually here
        addUser(new User("huba", "huba123", "employee"));
        addUser(new User("nico", "nico123", "employee"));
        addUser(new User("admin", "admin",  "manager"));
    }

    // --- User methods ---
    public List<String> getAllUserInitials() {
        return new ArrayList<>(allowedUsers.keySet());
    }

    public void addUser(User user) {
        allowedUsers.put(user.getInitials(), user);
    }

    public User getUser(String initials) {
        return allowedUsers.get(initials);
    }

    public Map<String, User> getAllUsers() {
        return new HashMap<>(allowedUsers);
    }

    // Add user to the project
    public void addUserToProject(String projectID, String initials) {
        Project project = projects.get(projectID);
        project.addMember(initials);
    }

    public void addProject(Project project) {
        projects.put(project.getProjectID(), project);
    }

    public Project getProject(String projectID) {
        return projects.get(projectID);
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects.values());
    }
}