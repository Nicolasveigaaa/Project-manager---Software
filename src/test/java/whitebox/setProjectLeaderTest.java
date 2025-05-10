package whitebox; 

import app.project.ProjectService;
import domain.Project;
import domain.User;
import persistence.Database;
import app.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// This code tests the setProjectLeader method
public class setProjectLeaderTest {

    private ProjectService projectService;
    private Database sharedDb;
    private Project testProject;
    private Project testProjectInstance;
    
    private String testProjectID = "proj123";
    private String testProjectName = "Project Test"; 
    private String testUserID = "huba"; 

    @BeforeEach
    void setUp() {
        // Get the database
        sharedDb = Main.getDatabase();
        if(sharedDb == null) {
            System.out.println("ERROR: No database found!");
            return;
        }
        // clear shared database
        sharedDb.resetDatabase();

        // add default user to database
        sharedDb.addUser(new User(testUserID, "employee"));
        // add testproject to database
        testProjectInstance = new Project(testProjectName, testProjectID); 
        sharedDb.addProject(testProjectInstance);

        // Create the service
        projectService = new ProjectService();

    }

    // Test when everything works
    @Test
    void testSetProjectLeader_Success() {

        // Try to set the leader
        try {
            projectService.setProjectLeader(testProjectID, testUserID);
        } catch(Exception e) {
            fail("This should not throw an exception!");
        }

        // Check if it worked
        if(sharedDb.getProject(testProjectID).isPresent()) {
            Project updatedProject = sharedDb.getProject(testProjectID).get();
            if(!testUserID.equals(updatedProject.getProjectLeaderInitials())) {
                fail("Project leader initials should be updated!");
            }
            else if(!testProjectID.equals(updatedProject.getProjectID())) {
                fail("Project ID should be the same!");
            }
            else if(!testProjectName.equals(updatedProject.getProjectName())) {
                fail("Project Name should be the same!");
            }
        } else {
            fail("Project should still exist!");
        }
    }

    // Test when project doesn't exist
    @Test
    void testSetProjectLeader_ProjectNotFound() {
        // Test data
        String fakeProjectId = "nonexistent";
        String leaderInitials = testUserID;

        // Try to set leader for non-existent project
        try {
            projectService.setProjectLeader(fakeProjectId, leaderInitials);
            fail("Should have thrown an exception!");
        } catch(IllegalArgumentException e) {
            // Make sure error message makes sense
            if(!e.getMessage().contains("Project with ID '" + fakeProjectId + "' not found")) {
                fail("Wrong error message!");
            }
        } catch(Exception e) {
            fail("Wrong type of exception!");
        }
    }

    // Test when user doesn't exist
    @Test
    void testSetProjectLeader_UserNotFound() {
        // Test data
        String fakeUserInitials = "xxxx";
        
        // Make sure user doesn't exist
        if(sharedDb.getUser(fakeUserInitials) != null) {
            fail("User 'xxxx' should not exist for this test!");
        }

        // Try to set leader with fake user
        try {
            projectService.setProjectLeader(testProjectID, fakeUserInitials);
            fail("Should have thrown an exception!");
        } catch(IllegalArgumentException e) {
            // Check error message
            if(!e.getMessage().contains("User with initials '" + fakeUserInitials + "' not found")) {
                fail("Wrong error message!");
            }
        } catch(Exception e) {
            fail("Wrong type of exception!");
        }
    }
}