// package hellocucumber.controllers;

// import io.cucumber.datatable.DataTable;
// import io.cucumber.java.Before;
// import io.cucumber.java.en.*;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;

// import static org.junit.jupiter.api.Assertions.*;
// import ui.Controllers.HomeScreenController;
// import domain.User;
// import domain.Project;
// import persistence.Database;

// import java.lang.reflect.Field;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;

// import app.Main;
// import app.employee.AuthValidation;
// import app.project.ProjectService;
// import javafx.embed.swing.JFXPanel;
// import javafx.event.ActionEvent;

// public class HomeScreenStep {
//     private HomeScreenController controller;
//     private Database db = Main.getDatabase();
//     private AuthValidation authValidation = new AuthValidation(db);
//     private User currentUser;
//     private ProjectService projectService = new ProjectService();

//     @Test
//     void initializeLoadsAssignedProjectsViaFXML() {
//         controller.initialize(); // projectsListView and labels are now non-null
//     }

//     @Before
//     public void setUp() throws Exception {
//         new JFXPanel();

//         // 1) Instantiate your controller
//         controller = new HomeScreenController();

//         // 2) Use reflection to inject new controls into its private @FXML fields
//         injectField("projectsListView", new ListView<Project>());
//         injectField("projectsCountLabel", new Label());
//         injectField("initialsLabel", new Label());
//         injectField("roleLabel", new Label());
//         injectField("openProject", new Button());

//         // Set user
//         currentUser = new User("admin", "admin", "Developer");
//         authValidation.validateLogin(currentUser.getInitials());
//     }

//     private <T> void injectField(String name, T value) throws Exception {
//         Field field = HomeScreenController.class.getDeclaredField(name);
//         field.setAccessible(true);
//         field.set(controller, value);
//     }

//     @Given("the user {string} with role {string} is logged in")
//     public void userIsLoggedIn(String initials, String role) {
//         currentUser = new User(initials, "", role);
//         // set current user context
//         authValidation.validateLogin(currentUser.getInitials());
//     }

//     @Given("the database contains the following projects:")
//     public void databaseContainsProjects(io.cucumber.datatable.DataTable table) {
//         List<Map<String, String>> rows = table.asMaps(String.class, String.class);
//         for (Map<String, String> row : rows) {
//             String id = row.get("projectID");
//             String name = row.get("projectName");
//             String members = row.get("memberInitials");
//             ProjectService.addProject(name);
//             db.addUserToProject(id, String.join(",", members.split(",")));
//         }
//     }

//     @When("the home screen loads")
//     public void homeScreenLoads() {
//         controller.initialize();
//     }

//     @Then("the user should see {int} projects in the list")
//     public void userSeesNumberOfProjects(int expectedCount) {
//         int actual = controller.getDisplayedProjectCount();
//         assertEquals(expectedCount, actual, "Displayed project count");
//     }

//     @Then("the projects list should contain {string} and {string}")
//     public void projectsListShouldContainTwo(String name1, String name2) {
//         List<String> names = controller.getDisplayedProjectNames();
//         assertTrue(names.contains(name1));
//         assertTrue(names.contains(name2));
//     }

//     @Then("the projects count label should display {string}")
//     public void projectsCountLabelShouldDisplay(String text) {
//         System.out.println(controller.getCountLabelText());
//         String label = controller.getCountLabelText();
//         assertEquals(text, label);
//     }

//     @Then("the initials label should display {string}")
//     public void initialsLabelShouldDisplay(String expected) {
//         System.out.println("HELLO THERE: " + controller.getInitialsText());
//         assertEquals(expected, controller.getInitialsText());
//     }

//     @Then("the role label should display {string}")
//     public void roleLabelShouldDisplay(String expected) {
//         assertEquals(expected, controller.getRoleText());
//     }

//     @Given("the home screen is loaded")
//     public void theHomeScreenIsLoaded() {
//         // Initialize the controller and mocks
//     }

//     @When("the user clicks the create project button")
//     public void theUserClicksTheCreateProjectButton() {
//         // Simulate a button click event
//         Button createProjectButton = new Button("Create Project");
//         ActionEvent event = new ActionEvent(createProjectButton, null);
//         controller.handleCreateProject(event);
//     }

//     @Then("the project creation screen should be displayed")
//     public void theProjectCreationScreenShouldBeDisplayed() {
//         // Verify that the project creation screen is displayed
//         // This could be done by checking if the ProjectCreationScreenController is active
//         // or by checking if the correct FXML has been loaded
        
//         // In a real implementation, you might use something like:
//         // assertTrue(projectCreationScreenIsActive());
//     }

//     @Given("the project creation screen is displayed")
//     public void theProjectCreationScreenIsDisplayed() {
//         // Setup the state where the project creation screen is displayed
//         // This might involve loading the correct FXML and controller
        
//         // For testing purposes, we're just assuming the screen is displayed
//     }

//     @When("the user creates a new project with:")
//     public void theUserCreatesANewProjectWith(DataTable dataTable) {
//         // Process the data table to create a new project
//         List<Map<String, String>> rows = dataTable.asMaps();
//         for (Map<String, String> row : rows) {
//             String name = row.get("name");
            
//             // Create a new project with the given data
//             Project project = new Project(name);
            
//             // Add project members if specified
//             if (row.containsKey("members")) {
//                 String[] members = row.get("members").split(",");
//                 for (String member : members) {
//                     project.getMemberInitials().add(member.trim());
//                 }
//             }
            
//             // Save the project (in a real implementation, this would use your project service)
//         }
//     }

//     @When("returns to the home screen")
//     public void returnsToTheHomeScreen() {
//         // Simulate returning to the home screen
//         controller.initialize();  // Reload projects and reset UI state
//     }

//     @Then("the projects list should include {string}")
//     public void theProjectsListShouldInclude(String projectName) {
//         // Check if the project is in the list
//         List<String> projectNames = controller.getDisplayedProjectNames();
//         assert(projectNames.contains(projectName));
//     }

//     @Then("the projects count label should be updated")
//     public void theProjectsCountLabelShouldBeUpdated() {
//         // Verify that the count label is updated to match the number of projects
//         int expectedCount = controller.getDisplayedProjectCount();
//         String actualCount = controller.getCountLabelText();
//         assert(actualCount.equals(String.valueOf(expectedCount)));
//     }

//     @Then("the open project button should be disabled")
//     public void theOpenProjectButtonShouldBeDisabled() {
//         // This would check if the open project button is disabled
//         // In a real implementation, you might use:
//         // assertTrue(openProjectButton.isDisabled());
//     }

//     @When("the user clicks the logout button")
//     public void theUserClicksTheLogoutButton() {
//         // Simulate clicking the logout button
//         Button logoutButton = new Button("Logout");
//         ActionEvent event = new ActionEvent(logoutButton, null);
//         controller.handleLogoutAction(event);
//     }

//     @Then("the user should be redirected to the login screen")
//     public void theUserShouldBeRedirectedToTheLoginScreen() {
//         // Verify the user is redirected to the login screen
//         // This might check if the current scene contains the login screen elements
        
//         // In a real implementation, you might use:
//         // assertTrue(currentSceneIsLoginScreen());
//     }

//     @When("the user selects the project {string} from the list")
//     public void theUserSelectsTheProjectFromTheList(String projectName) {
//         // Simulate selecting a project from the list
//         // In a real implementation, this would interact with the ListView
//         // For testing, we might directly set the selected item

//         List<Project> projects = db.getAllProjects();
        
//         // Find the project by name
//         for (Project project : projects) {
//             if (project.getProjectName().equals(projectName)) {
//                 // Select this project
//                 controller.projectsListView.getSelectionModel().select(project);
//                 break;
//             }
//         }
//     }

//     @Then("the open project button should be enabled")
//     public void theOpenProjectButtonShouldBeEnabled() {
//         // Check if the open project button is enabled
//         // In a real implementation, you might use:
//         // assertFalse(openProjectButton.isDisabled());
//     }

//     @Given("the user has selected the project {string} from the list")
//     public void theUserHasSelectedTheProjectFromTheList(String projectName) {
//         // Set up the state where a project is selected
//         theUserSelectsTheProjectFromTheList(projectName);
//     }

//     @When("the user clicks the open project button")
//     public void theUserClicksTheOpenProjectButton() {
//         // Simulate clicking the open project button
//         Button openButton = new Button("Open Project");
//         ActionEvent event = new ActionEvent(openButton, null);
//         controller.handleOpenProject(event);
//     }

//     @Then("the project service should open project with ID {string}")
//     public void theProjectServiceShouldOpenProjectWithID(String projectID) {
//         // Verify that the project service was called to open the project with the given ID
//         // In a real implementation with mocking, you might use:
//         // verify(projectService).openProject(projectID);
//     }

//     @Then("the user should be redirected to the project screen")
//     public void theUserShouldBeRedirectedToTheProjectScreen() {
//         // Verify that the user is redirected to the project screen
//         // This might check if the current scene contains the project screen elements
        
//         // In a real implementation, you might use:
//         // assertTrue(currentSceneIsProjectScreen());
//     }

//     @Then("the project screen should display {string} details")
//     public void theProjectScreenShouldDisplayDetails(String projectName) {
//         // Verify that the project screen displays the correct details
//         // This might check if the project name is displayed on the screen
        
//         // In a real implementation, you might access the project screen controller and check:
//         // assertEquals(projectName, projectScreenController.getDisplayedProjectName());
//     }
// }