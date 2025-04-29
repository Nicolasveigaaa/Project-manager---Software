package persistence;

// Folder imports
import domain.Employee;
import domain.Project;

// Java utilities
import java.util.*;

public class Database {

    private final Map<String, Employee> allowedUsers = new HashMap<>();
    private final Map<String, Project>  projects     = new HashMap<>();

    public Database() {
        // Hardcoded users — just add manually here
        addUser(new Employee("huba", "huba123", "user"));
        addUser(new Employee("nico", "nico123", "user"));
        addUser(new Employee("admin", "admin",  "admin"));
    }

    // --- User methods ---
    public List<String> getAllUserInitials() {
        return new ArrayList<>(allowedUsers.keySet());
    }

    public void addUser(Employee employee) {
        allowedUsers.put(employee.getInitials(), employee);
    }

    public Employee getUser(String initials) {
        return allowedUsers.get(initials);
    }

    public Map<String, Employee> getAllUsers() {
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