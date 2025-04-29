package persistence;

// Folder imports
import domain.User;
import domain.Project;

// Java imports
import java.util.*;

public class Database {
    private final Map<String, User> allowedUsers = new HashMap<>();
    private final Map<String, Project>  projects     = new HashMap<>();


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


    // --- Project methods ---
    public void createProject(String projectName, List<String> userInitials) {
        Project project = new Project(projectName, userInitials);
        projects.put(projectName, project);
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects.values());
    }
}