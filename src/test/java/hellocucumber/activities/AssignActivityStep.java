package hellocucumber.activities;

import domain.Activity;
import domain.Project;
import domain.User;

import org.junit.jupiter.api.Assertions;
import io.cucumber.java.en.*;

public class AssignActivityStep {

    private Project project = new Project("Project 1");
    private Activity activity;
    private User user;
    private Exception capturedException;

    @Given("an activity {string} exists in {string}")
    public void an_activity_exists_in_project(String activityName, String projectName) {
        // assume projectName matches the created project
        activity = new Activity(project, activityName, 0.0, 1, 2025, 1, 2025);
    }

    @Given("a user {string} exists")
    public void a_user_exists(String userName) {
        user = new User(userName, userName, userName);
    }

    @Given("{string} is already assigned to {string}")
    public void user_is_already_assigned_to_activity(String userName, String activityName) {
        // reuse existing user and activity
        activity.assignEmployee(userName);
    }

    @When("I assign {string} to activity {string}")
    public void i_assign_user_to_activity(String userName, String activityName) {
        capturedException = null;
        try {
            activity.assignEmployee(userName);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("{string} should be assigned to the activity")
    public void user_should_be_assigned_to_the_activity(String userName) {
        Assertions.assertEquals(true, activity.getAssignedEmployees().contains(userName));
    }

    @When("I unassign {string} from activity {string}")
    public void i_unassign_user_from_activity(String userName, String activityName) {
        capturedException = null;
        try {
            activity.unassignEmployee(userName);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("{string} should not be assigned to the activity")
    public void user_should_not_be_assigned_to_the_activity(String userName) {
        System.out.println(activity.getAssignedEmployees().contains(userName));
        Assertions.assertEquals(false, activity.getAssignedEmployees().contains(userName));
    }

    @When("I log {double} hours to activity {string}")
    public void i_log_hours_to_activity(double hours, String activityName) {
        capturedException = null;
        try {
            activity.logTime(hours);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the logged time of activity {string} should be {double}")
    public void the_logged_time_of_activity_should_be(String activityName, double expectedTime) {
        Assertions.assertEquals(expectedTime, activity.getLoggedTime(), 0.001);
    }
}
