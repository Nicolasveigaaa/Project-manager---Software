// Written by Nicolas V (s246060)

package whitebox;

// Domain
import domain.Activity;
import domain.Project;

// Test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WhiteBoxLogTimeTest {

    private Activity activity;

    @BeforeEach
    void setUp() {
        Project dummyProject = new Project("Test Project", "P001");
        activity = new Activity(dummyProject, "Development", 20.0, 10, 2025, 20, 2025);
    }

    @Test
    void testAddTimeEntry_valid() {
        activity.addTimeEntry("abc", "2025-05-01", 2.5);
        assertEquals(2.5, activity.getLoggedTime());
        assertEquals(1, activity.getTimeLog().size());
    }

    @Test
    void testAddTimeEntry_initialsNull_throws() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> activity.addTimeEntry(null, "2025-05-01", 2.0));
        assertEquals("Initials required.", e.getMessage());
    }

    @Test
    void testAddTimeEntry_initialsBlank_throws() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> activity.addTimeEntry("   ", "2025-05-01", 2.0));
        assertEquals("Initials required.", e.getMessage());
    }

    @Test
    void testAddTimeEntry_dateNull_throws() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> activity.addTimeEntry("abc", null, 2.0));
        assertEquals("Date required.", e.getMessage());
    }

    @Test
    void testAddTimeEntry_dateBlank_throws() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> activity.addTimeEntry("abc", "   ", 2.0));
        assertEquals("Date required.", e.getMessage());
    }

    @Test
    void testAddTimeEntry_negativeHours_throws() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> activity.addTimeEntry("abc", "2025-05-01", -3.0));
        assertEquals("Hours must be > 0", e.getMessage());
    }

    @Test
    void testAddTimeEntry_zeroHours_throws() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> activity.addTimeEntry("abc", "2025-05-01", 0.0));
        assertEquals("Hours must be > 0", e.getMessage());
    }
}