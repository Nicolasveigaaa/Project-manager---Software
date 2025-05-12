// [Written by s244706 and s246060] //

package persistence;

// Folder imports
import domain.User;
import domain.Project;

import java.time.Year;
// Java imports
import java.util.*;

public class Database {
    private final Map<String, User> allowedUsers = new HashMap<>();
    private final static Map<String, Project>  projects = new HashMap<>();

    // Constructor
    public Database() {
        // Hardcoded users — just add manually here
        addUser(new User("huba", "employee"));
        addUser(new User("nico", "employee"));
        addUser(new User("admin",  "manager"));
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
    
    // Add user to the project s244706
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
    // create the project ID s215062
    public String getNextProjectID() {
        
        int year = Year.now().getValue();
        year = year - 2000;
        while (year>99){
            year = year - 100;
        }
        int next = 1;

        for (String i : projects.keySet()) {
            if (String.valueOf(year).equals(i.substring(0,2))){
                next++;
            }
        }

        String nextID;
        if (next < 10){
             nextID = "00" + String.valueOf(next);
        } else if (next < 100){
            nextID = "0" + String.valueOf(next);
        } else {
            nextID = String.valueOf(next);
        }
        return (String.valueOf(year)+nextID);
    }

    // method for clearing database to a clean slate for testing
    public void resetDatabase() {
        projects.clear();
        allowedUsers.clear();
    }
}