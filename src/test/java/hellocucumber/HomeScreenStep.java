package hellocucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import static org.junit.jupiter.api.Assertions.*;
import ui.Controllers.AuthScreenController;
import ui.Controllers.HomeScreenController;
import domain.User;
import domain.Project;
import persistence.Database;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import app.Main;
import app.employee.AuthValidation;

import javafx.embed.swing.JFXPanel;

public class HomeScreenStep {
    private HomeScreenController controller;
    private Database db = Main.getDatabase();
    private AuthValidation authValidation = new AuthValidation(db);
    private User currentUser;

    @Test
    void initializeLoadsAssignedProjectsViaFXML() {
        controller.initialize(); // projectsListView and labels are now non-null
    }

    @Before
    public void setUp() throws Exception {
        new JFXPanel();

        // 1) Instantiate your controller
        controller = new HomeScreenController();

        // 2) Use reflection to inject new controls into its private @FXML fields
        injectField("projectsListView", new ListView<Project>());
        injectField("projectsCountLabel", new Label());
        injectField("initialsLabel", new Label());
        injectField("roleLabel", new Label());
        injectField("openProject", new Button());

        // Set user
        currentUser = new User("admin", "admin", "Developer");
        authValidation.validateLogin(currentUser.getInitials());
    }

    private <T> void injectField(String name, T value) throws Exception {
        Field field = HomeScreenController.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(controller, value);
    }

    @Given("the user {string} with role {string} is logged in")
    public void userIsLoggedIn(String initials, String role) {
        currentUser = new User(initials, "", role);
        // set current user context
        authValidation.validateLogin(currentUser.getInitials());
    }

    @Given("the database contains the following projects:")
    public void databaseContainsProjects(io.cucumber.datatable.DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String id = row.get("projectID");
            String name = row.get("projectName");
            String members = row.get("memberInitials");
            // db.saveProject(new Project(id, name, List.of(members.split(","))));
        }
    }

    @When("the home screen loads")
    public void homeScreenLoads() {
        controller.initialize();
    }

    @Then("the user should see {int} projects in the list")
    public void userSeesNumberOfProjects(int expectedCount) {
        int actual = controller.getDisplayedProjectCount();
        assertEquals(expectedCount, actual, "Displayed project count");
    }

    @Then("the projects list should contain {string} and {string}")
    public void projectsListShouldContainTwo(String name1, String name2) {
        List<String> names = controller.getDisplayedProjectNames();
        assertTrue(names.contains(name1));
        assertTrue(names.contains(name2));
    }

    @Then("the projects count label should display {string}")
    public void projectsCountLabelShouldDisplay(String text) {
        System.out.println(controller.getCountLabelText());
        String label = controller.getCountLabelText();
        assertEquals(text, label);
    }

    @Then("the initials label should display {string}")
    public void initialsLabelShouldDisplay(String expected) {
        System.out.println("HELLO THERE: " + controller.getInitialsText());
        assertEquals(expected, controller.getInitialsText());
    }

    @Then("the role label should display {string}")
    public void roleLabelShouldDisplay(String expected) {
        assertEquals(expected, controller.getRoleText());
    }

    @Given("the home screen is loaded")
    public void theHomeScreenIsLoaded() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the user clicks the create project button")
    public void theUserClicksTheCreateProjectButton() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the project creation screen should be displayed")
    public void theProjectCreationScreenShouldBeDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("the project creation screen is displayed")
    public void theProjectCreationScreenIsDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the user creates a new project with:")
    public void theUserCreatesANewProjectWith(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }

    @When("returns to the home screen")
    public void returnsToTheHomeScreen() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the projects list should include {string}")
    public void theProjectsListShouldInclude(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the projects count label should be updated")
    public void theProjectsCountLabelShouldBeUpdated() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the open project button should be disabled")
    public void theOpenProjectButtonShouldBeDisabled() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the user clicks the logout button")
    public void theUserClicksTheLogoutButton() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the user should be redirected to the login screen")
    public void theUserShouldBeRedirectedToTheLoginScreen() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the user selects the project {string} from the list")
    public void theUserSelectsTheProjectFromTheList(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the open project button should be enabled")
    public void theOpenProjectButtonShouldBeEnabled() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("the user has selected the project {string} from the list")
    public void theUserHasSelectedTheProjectFromTheList(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the user clicks the open project button")
    public void theUserClicksTheOpenProjectButton() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the project service should open project with ID {string}")
    public void theProjectServiceShouldOpenProjectWithID(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the user should be redirected to the project screen")
    public void theUserShouldBeRedirectedToTheProjectScreen() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the project screen should display {string} details")
    public void theProjectScreenShouldDisplayDetails(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}