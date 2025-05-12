package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import javafx.scene.Node;
import javafx.stage.Stage;
import ui.BaseController;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainStep {
    @Given("the application is started")
    public void applicationIsStarted() {
        // This step is handled by the JavaFXHook class
        // which starts the JavaFX application in a separate thread.
    }

    @Then("the auth screen should be visible")
    public void authScreenShouldBeVisible() {
        // Get the primary Stage and its Scene:
        Stage stage = BaseController.getStage();
        Node loginButton = stage.getScene().lookup("#loginButton");
        assertTrue(loginButton != null && loginButton.isVisible(),
                   "Expected #loginButton to be visible");
    }
}