// [Written by s246060]

package hellocucumber.activity;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Assertions;

import app.activity.AddTimeHandler;
import app.project.ProjectService;
import domain.Activity;
import domain.Project;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class addTimeStep {
    private ProjectService projectService = new ProjectService();
    private Project project;
    private String projectID;
    private Activity currentActivity;

    private Exception capturedException;

    private static final AtomicBoolean javaFxInitialized = new AtomicBoolean(false);

    @Before
    public void setUpDialogStub() {
        // Always “enter” 2.5 hours, no real UI.
        AddTimeHandler.setDialogSupplier(() -> Optional.of("2.5"));
    }

    @Before
    public void setUp() throws Exception {
        if (!javaFxInitialized.getAndSet(true)) {
            try {
                // Initialize JavaFX platform if not already initialized
                if (!Platform.isFxApplicationThread()) {
                    final CountDownLatch latch = new CountDownLatch(1);
                    Platform.startup(latch::countDown);
                    latch.await();
                }
            } catch (IllegalStateException e) {
                System.out.println("JavaFX toolkit was already initialized");
            }
        }

        // Now, initialize the components
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXML/activityCreationScreen.fxml"));
                Parent root = loader.load();
                loader.getController();

                // Setup a temporary stage to show the scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });
        latch.await(); // Wait for the JavaFX initialization to complete
    }

    @Given("a project in time handler {string} exists")
    public void aProjectInTimeHandlerExists(String projectName) {
        String id = ProjectService.addProject("Test3");
        Optional<Project> project = projectService.findProjectByID(id);
        this.project = project.get();
        this.projectID = this.project.getProjectID();
    }

    @Then("an activity in time handler {string} exists in {string}")
    public void an_activity_exists_in_project(String activityName, String projectName) {
        // assume projectName matches the created project
        projectService.createActivityForProject(projectID, activityName, 0.0, 1, 2025, 1, 2025);
        this.currentActivity = project.getActivityByName(activityName);

    }

    @When("I log the time spent in {string} in {string}")
    public void i_log_the_time_spent_in_in(String activityName, String projectName) {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                AddTimeHandler.handle(currentActivity);
                capturedException = null;
            } catch (Exception e) {
                capturedException = e;
            } finally {
                latch.countDown();
            }
        });
    }

    // TEST THE ADD TIME ENTRY //
    @When("I log the time spent in {string} date {string} and time {double}")
    public void i_log_time(String initials, String date, Double time) {
        // Find activity
        capturedException = null;
        try {
            this.currentActivity.addTimeEntry(initials, date, time);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @When("I log the time spent in null date {string} and time {double}")
    public void i_log_time_null_initials(String date, Double time) {
        // Find activity
        capturedException = null;
        try {
            this.currentActivity.addTimeEntry(null, date, time);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @When("I log the time spent in {string} date null and time {double}")
    public void i_log_time_null_date(String initials, Double time) {
        // Find activity
        capturedException = null;
        try {
            this.currentActivity.addTimeEntry(initials, null, time);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("success check for exception")
    public void success_check_for_exception() {
        if (capturedException != null) {
            System.out.println("Expected no exception but one was thrown: " + capturedException.getMessage());
            Assertions.fail("Expected no exception but one was thrown");
        }
    }

    @Then("check for exception {string}")
    public void check_for_exception(String expectedMessage) {
        if (capturedException == null) {
            System.out.println("Expected exception: " + expectedMessage);
            Assertions.fail("Expected an exception but none was thrown");
        }
        Assertions.assertEquals(expectedMessage, capturedException.getMessage());
    }
}
