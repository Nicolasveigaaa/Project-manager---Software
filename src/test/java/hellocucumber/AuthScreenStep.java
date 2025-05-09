package hellocucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.Controllers.AuthScreenController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


import static org.junit.jupiter.api.Assertions.*;

public class AuthScreenStep {
    private AuthScreenController authController = new AuthScreenController();
    private TextField adminInitialField;
    private Button loginButton;
    private boolean loginSucceeded;
     private Stage testStage;
    
    private static final AtomicBoolean javaFxInitialized = new AtomicBoolean(false);

    @Before
    public void setUp() throws Exception {
        // Initialize JavaFX toolkit only once
        if (!javaFxInitialized.getAndSet(true)) {
            try {
                // Try to initialize JavaFX platform if not already initialized
                if (!Platform.isFxApplicationThread()) {
                    final CountDownLatch latch = new CountDownLatch(1);
                    Platform.startup(latch::countDown);
                    latch.await();
                    System.out.println("JavaFX initialized successfully");
                }
            } catch (IllegalStateException e) {
                // Toolkit already initialized, which is fine
                System.out.println("JavaFX toolkit was already initialized");
            }
        }
    }

    @Given("user logs in with initials {string}")
    public void useLogsIn(String initials) throws Exception {
        authController.login(initials);
    }

    @Then("the login should succeed {string}")
    public void theLoginShouldSucceed(String expectedResult) {
        boolean expected = Boolean.parseBoolean(expectedResult);
        assertEquals(expected, loginSucceeded, 
            expected ? "Login should have succeeded" : "Login should have failed");
    }
}