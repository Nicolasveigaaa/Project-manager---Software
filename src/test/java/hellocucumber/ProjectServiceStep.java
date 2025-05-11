package hellocucumber;

import app.Main;
import app.project.ProjectService;
import domain.Activity;
import domain.Project;
import domain.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import persistence.Database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ProjectServiceStep {
    private ProjectService projectService;
    private Database database = Main.getDatabase();

    private String lastProjectId;
    private Optional<Project> lastProjectOpt;
    private Collection<Activity> lastActivities;
    private Map<String, Double> timeSummary;
    private Exception caughtException;

    @Before
    public void setup() {
        // reset database and initialize projectService
        this.projectService = new ProjectService();
        caughtException = null;
    }

    @Given("the database is reset")
    public void the_database_is_reset() {
        database = new Database();
        projectService = new ProjectService();
    }

    @Given("a user with initials {string} exists")
    public void a_user_with_initials_exists(String initials) {
        User u = new User(initials.toUpperCase(), initials);
        Main.getDatabase().addUser(u);
    }

    @When("I add a project named {string}")
    public void i_add_a_project_named(String name) {
        lastProjectId = ProjectService.addProject(name);
    }

    @Then("I can open the project by ID")
    public void i_can_open_the_project_by_id() {
        lastProjectOpt = projectService.openProject(lastProjectId);
        assertTrue(lastProjectOpt.isPresent());
    }

    @Then("finding by ID returns present")
    public void finding_by_id_returns_present() {
        Optional<Project> p = projectService.findProjectByID(lastProjectId);
        assertTrue(p.isPresent());
    }

    @When("I open project with ID {string}")
    public void i_open_project_with_id(String id) {
        lastProjectOpt = projectService.openProject(id);
    }

    @Then("finding by ID returns empty")
    public void finding_by_id_returns_empty() {
        assertFalse(lastProjectOpt.isPresent());
    }

    @When("I search for project by name {string}")
    public void i_search_for_project_by_name(String name) {
        lastProjectOpt = projectService.findProjectByName(name);
    }

    @Then("the result is {word}")
    public void the_result_is_word(String present) {
        boolean exp = Boolean.parseBoolean(present);
        assertEquals(exp, lastProjectOpt.isPresent());
    }

    @When("I create activity {string} on project {string}")
    public void i_create_activity_on_project(String act, String proj) {
        try {
            projectService.createActivityForProject(proj, act, 0.0, 1, 2, 2025, 2025);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("creation succeeds")
    public void creation_succeeds() {
        assertNull(caughtException);
    }

    @Then("creation duplicate")
    public void creation_duplicate() {
        assertNotNull(caughtException);
        assertTrue(caughtException.getMessage().contains("already exists"));
    }

    @Then("it fails with {string}")
    public void it_fails_with_message(String msg) {
        assertNotNull(caughtException);
        assertEquals(msg, caughtException.getMessage());
    }

    @When("I list activities for project {string}")
    public void i_list_activities_for_project(String proj) {
        try {
            lastActivities = projectService.getActivitiesForProject(proj);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("I see {int} activities")
    public void i_see_activities(Integer count) {
        assertNotNull(lastActivities);
        assertEquals(count.intValue(), lastActivities.size());
    }

    @When("I set project {string} leader to {string}")
    public void i_set_project_leader_to(String proj, String init) {
        try {
            projectService.setProjectLeader(proj, "null".equals(init) ? null : init);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("setting succeeds")
    public void setting_succeeds() {
        assertNull(caughtException);
    }

    @Then("setting null-error")
    public void setting_null_error() {
        assertNotNull(caughtException);
        assertTrue(caughtException.getMessage().contains("cannot be null"));
    }

    @Then("setting user-not-found")
    public void setting_user_not_found() {
        assertNotNull(caughtException);
        assertTrue(caughtException.getMessage().contains("not found"));
    }

    @When("I assign employee {string} to activity {string} in project {string}")
    public void i_assign_employee_to_activity_in_project(String emp, String act, String proj) {
        try {
            projectService.assignEmployeeToActivity(proj, act, emp);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @When("I log {double} hours on activity {string} in project {string}")
    public void i_log_hours_on_activity_in_project(Double hours, String act, String proj) {
        try {
            projectService.logTimeForActivity(proj, act, hours);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @When("I request time summary for project")
    public void i_request_time_summary_for_project() {
        try {
            timeSummary = projectService.getProjectTimeSummary(lastProjectId);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("the summary for {string} is {double}")
    public void the_summary_for_is(String act, Double val) {
        assertNotNull(timeSummary);
        assertEquals(val, timeSummary.get(act), 0.0001);
    }

    // Project leader is null
    @When("I set project {string} leader to null")
    public void i_set_project_leader_to_null(String proj) {
        try {
            projectService.setProjectLeader(proj, null);
        } catch (Exception e) {
            caughtException = e;
        }
    }
}
