// [Written by s244706 and s246060]

package persistence;

// Domain imports
import domain.User;
import domain.Project;

// Java imports
import java.util.*;
import java.time.Year;

public class Database {
    private final Map<String, User> allowedUsers = new HashMap<>();
    private final static Map<String, Project> projects = new HashMap<>();

    // Constructor
    public Database() {
        // Hardcoded users — roles removed
        addUser(new User("huba"));
        addUser(new User("nico"));
        addUser(new User("jaco"));
        addUser(new User("Vinc"));
        addUser(new User("Fred"));
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

    public Optional<Project> getProject(String projectID) {
        return Optional.ofNullable(projects.get(projectID));
    }

    public void addUserToProject(String projectID, String initials) {
        Project project = projects.get(projectID);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID '" + projectID + "' not found. Cannot add user '" + initials + "'.");
        }
        project.addMember(initials);
    }

    public void addProject(Project project) {
        projects.put(project.getProjectID(), project);
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects.values());
    }
    // [Written by s215062 ] //
    // Generate project ID like 25001, 25002 etc.
    public String getNextProjectID() {
        int year = Year.now().getValue() - 2000;
        if (year > 99) year -= 100;

        int next = 1;
        for (String id : projects.keySet()) {
            if (id.startsWith(String.valueOf(year))) {
                next++;
            }
        }

        String formatted = String.format("%03d", next);
        return year + formatted;
    }

    public void resetDatabase() {
        projects.clear();
        allowedUsers.clear();
    }
}