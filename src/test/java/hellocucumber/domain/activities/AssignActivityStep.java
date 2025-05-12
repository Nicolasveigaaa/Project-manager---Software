package hellocucumber.domain.activities;

import domain.Activity;
import domain.Project;
import domain.User;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import app.project.ProjectService;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class AssignActivityStep {
    private Project project;
    private Activity activity;
    private Exception capturedException;
    private String projectID;
    private ProjectService projectService = new ProjectService();
    private User user;

    public AssignActivityStep() {
        // simple Project stub; implement real Project constructor as needed
        String projectID = ProjectService.addProject("activityTestProject");
        Optional<Project> project = projectService.findProjectByID(projectID);
        this.project = project.get();
        this.projectID = this.project.getProjectID();
        this.activity = new Activity(this.project, "Activity", 1.0, 1, 2025, 2, 2025);
    }

    @Given("an activity {string} exists in {string}")
    public void an_activity_exists_in_project(String activityName, String projectName) {
        // assume projectName matches the created project
        projectService.createActivityForProject(projectID, activityName, 0.0, 1, 2025, 1, 2025);
    }

    @Given("a user {string} exists")
    public void a_user_exists(String userName) {
        user = new User(userName, userName);
        user.getRole();
        user.getInitials();
        user.getTotalHours();
    }

    @Given("{string} is already assigned to {string}")
    public void user_is_already_assigned_to_activity(String userName, String activityName) {
        // reuse existing user and activity
        projectService.assignEmployeeToActivity(projectID, activityName, userName);
    }

    @When("I assign null to activity {string}")
    public void i_assign_null_to_activity(String activityName) {
        capturedException = null;
        try {
            projectService.assignEmployeeToActivity(projectID, activityName, null);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @When("I assign {string} to activity {string}")
    public void i_assign_user_to_activity(String userName, String activityName) {
        capturedException = null;
        try {
            projectService.assignEmployeeToActivity(projectID, activityName, userName);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("{string} should be assigned to the activity")
    public void user_should_be_assigned_to_the_activity(String userName) {
        this.activity = project.getActivityByName("Design");
        Assertions.assertEquals(true, activity.getAssignedEmployees().contains(userName));
    }

    @When("I unassign {string} from activity {string}")
    public void i_unassign_user_from_activity(String userName, String activityName) {
        // Get actibity
        Activity activity2 = project.getActivityByName(activityName);
        capturedException = null;
        try {
            activity2.unassignEmployee(userName);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @When("I unassign null from activity {string}")
    public void i_unassign_null_from_activity(String activityName) {
        Activity activity2 = project.getActivityByName(activityName);
        capturedException = null;
        try {
            activity2.unassignEmployee(null);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("{string} should not be assigned to the activity")
    public void user_should_not_be_assigned_to_the_activity(String userName) {
        Assertions.assertEquals(false, activity.getAssignedEmployees().contains(userName));
    }

    private boolean result = false;

    @When("I check if {string} is assigned to activity {string}")
    public void i_check_if_user_is_assigned_to_activity(String userName, String activityName) {
        Activity activity2 = project.getActivityByName(activityName);
        String newInitials = userName;

        if (userName.matches("null")) {
            newInitials = null;
        }

        capturedException = null;
        result = false;
        try {
            activity2.isAssigned(newInitials);
            result = true;
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @When("I assign {string} to activity null")
    public void iAssignToActivityNull(String initials) {
        capturedException = null;

        // get activity
        try {
            projectService.assignEmployeeToActivity(projectID, null, initials);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("I should get a result of {string}")
    public void i_should_get_a_result_of(String expectedResult) {
        // Transform string to boolean
        boolean expectedBooleanResult = Boolean.parseBoolean(expectedResult);
        Assertions.assertEquals(expectedBooleanResult, result);
    }

    @Then("I should get an error from Assign Activity {string}")
    public void i_should_get_an_error(String expectedMessage) {
        if (capturedException == null) {
            System.out.println("Expected exception: " + expectedMessage);
            Assertions.fail("Expected an exception but none was thrown");
        }
        Assertions.assertEquals(expectedMessage, capturedException.getMessage());
    }
}
