 // [Written by s244706] //

package hellocucumber.domain.activities;

import domain.Activity;
import domain.Project;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import app.project.ProjectService;
import io.cucumber.java.en.*;

public class CreateActivityStep {
    private Project project;
    private Activity activity;
    private Exception exception;
    private String projectID;
    private ProjectService projectService = new ProjectService();

    @Given("a project {string} exists")
    public void a_project_exists(String projectName) {
        // simple Project stub; implement real Project constructor as needed
        String id = ProjectService.addProject(projectName);
        Optional<Project> project = projectService.findProjectByID(id);
        this.project = project.get();
        this.projectID = this.project.getProjectID();
    }

    @When("I create an activity {string} with budgeted time {double}, start week {int}, start year {int}, end week {int}, end year {int} for {string}")
    public void i_create_an_activity_with_parameters_for_project(
        String name,
        double budgetedTime,
        int startWeek,
        int startYear,
        int endWeek,
        int endYear,
        String projectName
    ) {
        try {
            projectService.createActivityForProject(projectID, name, budgetedTime, startWeek, endWeek, startYear, endYear);
            this.activity = project.getActivityByName(name);
            // assume projectName matches our stored project
            //this.activity = new Activity(project, name, budgetedTime, startWeek, startYear, endWeek, endYear);
            this.exception = null;
        } catch (Exception e) {
            System.out.println("Creating successfully failed: " + e.getMessage());
            this.exception = e;
        }
    }

    @When("I try to create an activity {string} without a project")
    public void i_try_to_create_an_activity_without_a_project(String name) {
        try {
            projectService.createActivityForProject(null, name, 1.0, 1, 2025, 2, 2025);

            this.exception = null;
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Then("I check if everythign is set up correctly")
    public void i_check_if_everything_is_set_up_correctly() {
        assertNotNull(activity.getStartWeek());
        assertNotNull(activity.getStartYear());
        assertNotNull(activity.getEndWeek());
        assertNotNull(activity.getEndYear());
    }

    @When("I try to create an activity with an empty name for {string}")
    public void i_try_to_create_an_activity_with_an_empty_name(String activityName) {
        try {
            projectService.createActivityForProject(projectID, activityName, 1.0, 1, 2025, 2, 2025);
            this.exception = null;
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @And("I try to create an activity {string} with budgeted time {double}")
    public void i_try_to_create_an_activity_with_negative_budgeted_time(String activityName, double budgetedTime) {
        try {
            projectService.createActivityForProject(projectID, activityName, budgetedTime, 1, 2025, 2, 2025);
            this.exception = null;
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Then("the activity {string} should be created successfully")
    public void the_activity_should_be_created_successfully(String expectedName) {
        Assertions.assertNull(exception, "Expected no exception, but got: " + exception);
        Assertions.assertNotNull(activity, "Activity should not be null");
        Assertions.assertEquals(expectedName, activity.getName());
    }

    @Then("its budgeted time should be {double}")
    public void its_budgeted_time_should_be(Double expectedBudget) {
        Assertions.assertEquals(expectedBudget, activity.getBudgetedTime());
    }

    @Then("it should belong to {string}")
    public void it_should_belong_to(String expectedProjectName) {
        Assertions.assertEquals(expectedProjectName, activity.getProject().getProjectName());
    }

    @Then("I should get an error {string}")
    public void i_should_get_an_error(String expectedMessage) {
        if (exception == null) {
            System.out.println("Expected exception: " + expectedMessage);
            Assertions.fail("Expected an exception but none was thrown");
        }
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    // Check budgeted time
    @And("an activity {string} with budgeted time {double} exists in {string}")
    public void an_activity_with_budgeted_time_exists_in_project(String activityName, double budgetedTime, String projectName) {
        projectService.findProjectByID(projectID).ifPresent(project -> {
            Assertions.assertNotNull(this.activity, "Activity should not be null");
            Assertions.assertEquals(budgetedTime, this.activity.getBudgetedTime(), 0.01);
        });
    }

    @When("I change the budgeted time of activity {string} to {double}")
    public void i_change_the_budgeted_time_of_activity_to(String activityName, double budgetedTime) {
        try {
            activity.setBudgetedTime(budgetedTime);
        } catch (Exception e) {
            exception = e;
        }
    }
}
