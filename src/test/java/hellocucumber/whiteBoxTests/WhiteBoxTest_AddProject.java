package hellocucumber.whiteBoxTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import app.employee.AuthValidation;
import app.project.ProjectService;
import domain.Project;
import domain.User;
import persistence.Database;

public class WhiteBoxTest_AddProject {
    // Mocked dependencies
    private Database mockDb;
    private MockedStatic<AuthValidation> authMock;

    @BeforeEach
    void setUp() throws Exception {
        // Mock the database
        mockDb = mock(Database.class);
        // Inject mock into service (assume ProjectService.db is package-private static)
        java.lang.reflect.Field dbField = ProjectService.class.getDeclaredField("db");
        dbField.setAccessible(true);
        dbField.set(null, mockDb);

        // Mock AuthValidation.getCurrentUser()
        authMock = mockStatic(AuthValidation.class);
        User fakeUser = new User("AB", "employee");
        authMock.when(AuthValidation::getCurrentUser).thenReturn(fakeUser);
    }

    @AfterEach
    void tearDown() {
        authMock.close();
    }

    // Test that a project can be created through Project Service
    @Test
    void Test_Create_Project() {
        String projectName = "TestProject";

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        doNothing().when(mockDb).addProject(captor.capture());

        // Call method under test
        String returnedId = ProjectService.addProject(projectName);

        // The returned ID should match the project's ID format
        assertNotNull(returnedId);

        // Verify only add the project once
        verify(mockDb, times(1)).addProject(any(Project.class));
        
        
        Project capturedProject = captor.getValue();
        when(mockDb.getProject(returnedId)).thenReturn(Optional.of(capturedProject));
    }

    // Test when project name is empty
    @Test
    void Test_Create_Project_with_EmptyName() {
        String projectName = "";

        // Call method under test
        String returnedId = ProjectService.addProject(projectName);

        // The returned ID should be null
        assertNull(returnedId);

        // Verify the project was not added to the database
        verify(mockDb, times(0)).addProject(any(Project.class));
    }

    // Test if the user who creates the project is added as a member
    @Test
    void Test_Project_Add_Member() {
        String name = "Demo";
        Project project = new Project(name, "2025-1");

        // Check project name
        assertEquals(name, project.getProjectName());

        // No project manager given in the start
        assertNull(project.getProjectLeaderInitials());

        // ID format
        String id = project.getProjectID();
        assertNotNull(id);

        // Member list contains current user
        List<String> members = project.getMemberInitials();
        assertNotNull(members);
        // Check that only 1 member is added
        assertEquals(1, members.size(), "Should have one member initially");
        // Check if initials where put in lower case
        assertEquals("ab", members.get(0), "Initials of the member should be of the current user");
    }

    // Test if the created project has the correct ID format
    @Test
    void Test_Project_ID() {
        String name = "Demo";
        Project project = new Project(name, "2025-1");
        String id = project.getProjectID();

        assertNotNull(id);
        assertTrue(id.startsWith("2025-"), "ID should start with '2025-'");
        assertEquals(6, id.length(), "ID should be 21 characters long (5 for '2025-' + 16 random)");
    }

    // Test if the created project has added all the essential variables
    @Test
    void Test_Project_Variables() {
        String name = "Demo";
        Project project = new Project(name, "2025-1");

        // Check project name
        assertEquals(name, project.getProjectName());

        // No project manager given in the start
        assertNull(project.getProjectLeaderInitials());

        // ID format
        String id = project.getProjectID();
        assertNotNull(id);

        // Member list contains current user
        List<String> members = project.getMemberInitials();
        assertNotNull(members);
        // Check that only 1 member is added
        assertEquals(1, members.size(), "Should have one member initially");
        // Check if initials where put in lower case
        assertEquals("ab", members.get(0), "Initials of the member should be of the current user");

        // Check if the project is empty
        assertTrue(project.getActivities().isEmpty(), "Project should be empty");
    }

    // Test with a special character in the project name
    @Test
    void Test_Project_ID_with_special_characters() {
        String name = "   ";
        String id = ProjectService.addProject(name);

        // Check if the project was not created
        assertNull(id);
    }
}