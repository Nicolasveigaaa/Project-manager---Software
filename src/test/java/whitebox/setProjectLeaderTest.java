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
    private User testUser;
    private String testProjectId;
    
    private String TEST_PROJECT_NAME = "Project Test"; 
    private String TEST_USER_INITIALS = "huba"; 

    @BeforeEach
    void setUp() {
        // Get the database
        sharedDb = Main.getDatabase();
        if(sharedDb == null) {
            System.out.println("ERROR: No database found!");
            return;
        }
        
        // Create the service
        projectService = new ProjectService();

        // Check if project exists
        Boolean projectFound = false;
        for(Project p : sharedDb.getAllProjects()) {
            if(p.getProjectName().equals(TEST_PROJECT_NAME)) {
                testProject = p;
                testProject.setProjectLeaderInitials(null);
                projectFound = true;
                break;
            }
        }
        
        // If not found, create new one
        if(projectFound == false) {
            testProject = new Project(TEST_PROJECT_NAME);
            sharedDb.addProject(testProject);
        }
        
        // Save project ID
        testProjectId = testProject.getProjectID();

        // Get test user
        testUser = sharedDb.getUser(TEST_USER_INITIALS);
        if(testUser == null) {
            System.out.println("ERROR: User 'huba' not found!");
        }
    }

    // Test when everything works
    @Test
    void testSetProjectLeader_Success() {
        // Set up test data
        String leaderInitials = TEST_USER_INITIALS;

        // Try to set the leader
        try {
            projectService.setProjectLeader(testProjectId, leaderInitials);
        } catch(Exception e) {
            fail("This should not throw an exception!");
        }

        // Check if it worked
        if(sharedDb.getProject(testProjectId).isPresent()) {
            Project updatedProject = sharedDb.getProject(testProjectId).get();
            if(!leaderInitials.equals(updatedProject.getProjectLeaderInitials())) {
                fail("Project leader initials should be updated!");
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
        String leaderInitials = TEST_USER_INITIALS;

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
            projectService.setProjectLeader(testProjectId, fakeUserInitials);
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