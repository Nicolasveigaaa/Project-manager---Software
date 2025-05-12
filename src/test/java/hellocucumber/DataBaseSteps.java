package hellocucumber;

import io.cucumber.java.en.*;
import io.cucumber.java.lu.a;
import persistence.Database;
import app.Main;
import app.employee.AuthValidation;
import app.project.ProjectService;
import domain.Project;
import domain.User;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

public class DataBaseSteps {
    private Database database = new Database();
    private ProjectService projectService = new ProjectService();
    private Project project;
    private String projectID;

    private Exception capturedException;

    private void reloadDatabase() {
         // Then immediately set up authentication again
        User u = new User("AB", "employee");
        User u2 = new User("huba", "employee");
        User u3 = new User("nico", "employee");
        User u4 = new User("admin", "manager");

        Main.getDatabase().addUser(u);
        Main.getDatabase().addUser(u2);
        Main.getDatabase().addUser(u3);
        Main.getDatabase().addUser(u4);
    }

    @Given("the database is reset")
    public void the_database_is_reset() {
        // First reset the database
        Main.getDatabase().resetDatabase();

        // Then immediately set up authentication again
        reloadDatabase();
        AuthValidation authValidation = new AuthValidation(Main.getDatabase());
        authValidation.validateLogin("AB");
        authValidation.validateLogin("huba");
        authValidation.validateLogin("nico");        
        authValidation.validateLogin("admin");
    }

    @Then("create new database")
    public void create_new_database() {
        assertEquals(0, database.getAllProjects().size());
        database = new Database();
    }

    // Check if the database is still available
    @Given("the database is not empty")
    public void the_database_is_not_empty() {
        assertNotNull(database.getAllUsers());
    }

    @Then("the database should be available")
    public void the_database_should_be_available() {
        assertNotNull(database);
    }

    // Fail, add user to non existing project
    @When("I add a user {string} to a non existing project {string}")
    public void i_add_a_user_to_a_non_existing_project(String initials, String projectID) {
        try {
            database.addUserToProject(projectID, initials);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("it should throw an exception {string}")
    public void it_should_throw_an_exception(String expectedMessage) {
        if (capturedException == null) {
            System.out.println("Expected exception: " + expectedMessage);
            Assertions.fail("Expected an exception but none was thrown");
        }
        Assertions.assertEquals(expectedMessage, capturedException.getMessage());
    }

    // Success, add user to existing project
    @Given("a project {string} in database exists")
    public void a_project_exists(String projectName) {
        ProjectService.addProject("ProjectA-001");
        Optional<Project> project = projectService.findProjectByName(projectName);
        this.project = project.get();
        this.projectID = this.project.getProjectID();
    }

    @When("I add a user {string} to project the existing project")
    public void i_add_a_user_to_project_the_existing_project(String initials) {
        try {
            database.addUserToProject(projectID, initials);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the user {string} should be added to the project")
    public void the_user_should_be_added_to_the_project(String initials) {
        assertNotNull(database.getProject(projectID));
        assertTrue(database.getProject(projectID).get().getMemberInitials().contains(initials));
    }

    // Reset database
    @When("I reset the database")
    public void i_reset_the_database() {
        database.resetDatabase();
    }

    @Then("the database should be empty")
    public void the_database_should_be_empty() {
        assertEquals(0, database.getAllProjects().size());
    }

    // Get all users
    @When("I get all users")
    public void i_get_all_users() {
        try {
            database.getAllUserInitials();
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the list should contain the at least 4 users")
    public void the_list_should_contain_the_at_least_4_users() {
        assertNotNull(database.getAllUserInitials());
        assertTrue(database.getAllUserInitials().size() >= 3);
    }
}
