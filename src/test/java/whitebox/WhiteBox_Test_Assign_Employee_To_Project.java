package whitebox;

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
import domain.Activity;
import domain.Project;
import domain.User;
import persistence.Database;

public class WhiteBox_Test_Assign_Employee_To_Project {
    // Mocked dependencies
    private Database mockDb;
    private MockedStatic<AuthValidation> authMock;
    private ProjectService projectService = new ProjectService();

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

    // Test assigning an employee to an activity is successful
    @Test
    void testAssignEmployeeToActivity_success() {
        String projectID = "pid";
        String activityName = "act";
        String initials = "XY";

        Activity mockAct = mock(Activity.class);
        doNothing().when(mockAct).assignEmployee(initials);

        Project mockProj = mock(Project.class);
        when(mockProj.getActivityByName(activityName)).thenReturn(mockAct);
        when(mockDb.getProject(projectID)).thenReturn(Optional.of(mockProj));

        // No exception = success
        projectService.assignEmployeeToActivity(projectID, activityName, initials);
        verify(mockAct, times(1)).assignEmployee(initials);
    }

    // Test if you assign employee to an activity where the project does not exist
    @Test
    void testAssignEmployeeToActivity_projectNotFound() {
        when(mockDb.getProject("pid")).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> projectService.assignEmployeeToActivity("pid", "act", "XY")
        );
        assertEquals("Project with ID 'pid' not found.", ex.getMessage());
    }

    // Test if you assign employee to an activity where the activity does not exist
    @Test
    void testAssignEmployeeToActivity_activityNotFound() {
        Activity mockAct = mock(Activity.class);
        
        Project mockProj = mock(Project.class);
        when(mockDb.getProject("pid")).thenReturn(Optional.of(mockProj));
        when(mockProj.getActivityByName("act")).thenReturn(mockAct);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> projectService.assignEmployeeToActivity("pid", "cool", "XY")
        );
        assertEquals("Activity with name: 'cool' not found.", ex.getMessage());
    }

    // Test if you try to assign an employee to an activity that is already assigned
    @Test
    void testAssignEmployeeToActivity_alreadyAssigned() {
        Activity mockAct = mock(Activity.class);
        doThrow(new IllegalArgumentException("already assigned")).when(mockAct).assignEmployee("XY");

        Project mockProj = mock(Project.class);
        when(mockDb.getProject("pid")).thenReturn(Optional.of(mockProj));
        when(mockProj.getActivityByName("act")).thenReturn(mockAct);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> projectService.assignEmployeeToActivity("pid", "act", "XY")
        );
        assertTrue(ex.getMessage().contains("Could not assign employee: already assigned"));
    }

    // Test if the employee initials are null
    @Test
    void testAssignEmployeeToActivity_employeeInitialsNull() {
        Activity mockAct = mock(Activity.class);
        doThrow(new IllegalArgumentException("already assigned")).when(mockAct).assignEmployee("XY");

        Project mockProj = mock(Project.class);
        when(mockDb.getProject("pid")).thenReturn(Optional.of(mockProj));
        when(mockProj.getActivityByName("act")).thenReturn(mockAct);
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> projectService.assignEmployeeToActivity("pid", "act", null)
        );
        assertEquals("Cannot assign null user.", ex.getMessage());
    }

    // Test when project ID is null
    @Test
    void testAssignEmployeeToActivity_projectIDNull() {
        Activity mockAct = mock(Activity.class);

        // Project exists
        Project mockProj = mock(Project.class);
        when(mockDb.getProject("pid")).thenReturn(Optional.of(mockProj));
        when(mockProj.getActivityByName("act")).thenReturn(mockAct);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> projectService.assignEmployeeToActivity(null, "act", "XY")
        );
        
        assertEquals("Project with ID 'null' not found.", ex.getMessage());
    }
}
