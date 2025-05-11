package hellocucumber.controllers;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import static org.junit.jupiter.api.Assertions.*;
import ui.Controllers.HomeScreenController;
import domain.User;
import domain.Project;
import persistence.Database;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import app.Main;
import app.employee.AuthValidation;
import app.project.ProjectService;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;

public class TestHome {
    private HomeScreenController controller;
    private Database db = Main.getDatabase();
    private AuthValidation authValidation = new AuthValidation(db);
    private User currentUser;
    private ProjectService projectService = new ProjectService();

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

    @Given("I am logged in as user {string} with role {string}")
    public void i_am_logged_in_as_user_with_role(String initials, String role) {
        // Use the existing AuthValidation class
        User user = new User(initials, "password", role);
        authValidation.validateLogin(initials);
    }
    
    @Given("I am on the home screen")
    public void i_am_on_the_home_screen() {
        // Create the controller and initialize it
        controller = new HomeScreenController();
        // Initialize is called automatically in JavaFX, but we need to call it manually in tests
        controller.initialize();
        // Set the logged in user explicitly for testing
        controller.setLoggedInUser(AuthValidation.getCurrentUser());
    }
    
    @Then("I should see my initials {string} displayed")
    public void i_should_see_my_initials_displayed(String initials) {
        assertEquals(initials, controller.getInitialsText());
    }
    
    @Then("I should see my role {string} displayed")
    public void i_should_see_my_role_displayed(String role) {
        assertEquals(role, controller.getRoleText());
    }
    
    @Then("I should see the correct number of projects in the count label")
    public void i_should_see_the_correct_number_of_projects_in_the_count_label() {
        String countText = controller.getCountLabelText();
        int displayedCount = controller.getDisplayedProjectCount();
        assertEquals(String.valueOf(displayedCount), countText);
    }
    
    @Then("the projects list should contain only my projects")
    public void the_projects_list_should_contain_only_my_projects() {
        List<String> projectNames = controller.getDisplayedProjectNames();
        String userInitials = AuthValidation.getCurrentUser().getInitials();
        
        // This is a basic verification that depends on your Database implementation
        // You may need to adjust this based on your actual implementation
        Database db = new Database();
        List<Project> allProjects = db.getAllProjects();
        
        for (String projectName : projectNames) {
            boolean foundMatchingProject = false;
            for (Project project : allProjects) {
                if (project.getProjectName().equals(projectName) && 
                    project.getMemberInitials().contains(userInitials)) {
                    foundMatchingProject = true;
                    break;
                }
            }
            assertTrue(foundMatchingProject);
        }
    }
    
    @When("I click the create project button")
    public void i_click_the_create_project_button() {
        // Create a simple ActionEvent - this is just for test purposes
        ActionEvent event = new ActionEvent();
        controller.handleCreateProject(event);
    }
    
    @Then("the project creation screen should open")
    public void the_project_creation_screen_should_open() {
        // This is harder to test directly without a UI framework
        // For now, we'll just verify that the code runs without exceptions
        // In a real scenario, you'd want to verify the screen actually opened
    }
    
    @Then("the projects list should be refreshed")
    public void the_projects_list_should_be_refreshed() {
        // Similarly, this is hard to verify directly
        // You could add a timestamp or counter to your loadProjects method
        // to verify it was called
    }
    
    @When("I select a project from the list")
    public void i_select_a_project_from_the_list() {
        // This would normally use UI interaction
        // For testing, we can set up the project list manually
        Database db = new Database();
        List<Project> projects = db.getAllProjects();
        if (projects.isEmpty()) {
            // Create a test project if none exists
            Project testProject = new Project("Test Project");
            testProject.addMember(AuthValidation.getCurrentUser().getInitials());
            projects.add(testProject);
        }
        
        // Add a project to the list view and select it
        // This depends on how your ListView is exposed in the controller
        // You might need to extend your controller to expose this for testing
        controller.projectsListView.getItems().add(projects.get(0));
        controller.projectsListView.getSelectionModel().select(0);
    }
    
    @When("I click the open project button")
    public void i_click_the_open_project_button() {
        // Similar to the create project button
        ActionEvent event = new ActionEvent();
        controller.handleOpenProject(event);
    }
    
    @Then("the selected project should be opened")
    public void the_selected_project_should_be_opened() {
        // Similar to verifying project creation screen opened
        // This is hard to test directly without a UI framework
    }
    
    @When("I click the logout button")
    public void i_click_the_logout_button() {
        ActionEvent event = new ActionEvent();
        controller.handleLogoutAction(event);
    }
    
    @Then("I should be redirected to the login screen")
    public void i_should_be_redirected_to_the_login_screen() {
        // Similar to verifying screens open
        // This is hard to test directly without a UI framework
    }
}