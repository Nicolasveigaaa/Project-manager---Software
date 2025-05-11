package hellocucumber.domain.activities;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import app.project.ProjectService;
import domain.Activity;
import domain.Project;
import domain.User;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class logTimeInfoStep {
    private Project project;
    private Activity activity;
    private Exception capturedException;
    private String projectID;
    private ProjectService projectService = new ProjectService();

    public logTimeInfoStep() {
        // simple Project stub; implement real Project constructor as needed
        String projectID = ProjectService.addProject("activityTestProject");
        Optional<Project> project = projectService.findProjectByID(projectID);
        this.project = project.get();
        this.projectID = this.project.getProjectID();
        this.activity = new Activity(this.project, "Development", 30.0, 1, 2025, 2, 2025);
    }

    @When("I log {double} hours to activity {string}")
    public void i_log_hours_to_activity(double hours, String activityName) {
        capturedException = null;
        try {
            projectService.logTimeForActivity(projectID, activityName, hours);
            activity.logTime(hours);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the logged time of activity {string} should be {double}")
    public void the_logged_time_of_activity_should_be(String activityName, double expectedTime) {
        Assertions.assertEquals(expectedTime, activity.getLoggedTime(), 0.001);
    }

    @Then("I should get an error from log time {string}")
    public void i_should_get_an_error(String expectedMessage) {
        if (capturedException == null) {
            System.out.println("Expected exception: " + expectedMessage);
            Assertions.fail("Expected an exception but none was thrown");
        }
        Assertions.assertEquals(expectedMessage, capturedException.getMessage());
    }
}
