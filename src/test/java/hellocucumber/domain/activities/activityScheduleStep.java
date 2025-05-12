 // [Written by s244706] //

package hellocucumber.domain.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import app.employee.AuthValidation;
import app.project.ProjectService;
import domain.Activity;
import domain.Project;
import domain.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class activityScheduleStep {
    private Project project;
    private Activity activity;
    private Exception exception;
    private String projectID;
    private ProjectService projectService = new ProjectService();

    private boolean result;

    public activityScheduleStep() {
        // simple Project stub; implement real Project constructor as needed
        String id = ProjectService.addProject("Test3");
        Optional<Project> project = projectService.findProjectByID(id);
        this.project = project.get();
        this.projectID = this.project.getProjectID();
    }

    @Given("an activity from week {int} of {int} to week {int} of {int}")
    public void an_activity_from_to(int startWeek, int startYear, int endWeek, int endYear) {
        this.activity = new Activity(this.project, "Activity", 20.0, startWeek, startYear, endWeek, endYear);
    }

    @When("I check if the activity is active in week {int} of {int}")
    public void i_check_if_the_activity_is_active_in_week_of_year(int week, int year) {
        result = activity.isActiveIn(week, year);
    }

    @Then("the result should be {word}")
    public void the_result_should_be(String expectedStr) {
        boolean expected = Boolean.parseBoolean(expectedStr);
        assertEquals(expected, result);
    }

    // Check for tostring
    @Test
    public void testToString() {
        User user = new User("Check"); // adapt this to your actual User class
        //AuthValidation authValidation = new AuthValidation(null); // Pass the database if needed
        System.out.println("This user:" + user);
        System.out.println("User initials: " + user.getInitials());
        //authValidation.validateLogin(user.getInitials());

        // activity = new Activity(this.project, "Design", 100.0, 1, 2023, 52, 2023);
        // activity.setBudgetedTime(100.0);
        // activity.setLoggedTime(42.5);
        // activity.assignEmployee("Alice");
        // activity.assignEmployee("Bob");

        // String expected = "Design (Budget: 100.0, Logged: 42.5, Assigned: 2)";
        // assertEquals(expected, activity.toString());
    }
}
