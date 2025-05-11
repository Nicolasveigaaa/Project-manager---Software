package hellocucumber.controllers;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Controllers.AuthScreenController;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import app.employee.AuthValidation;

import static org.junit.jupiter.api.Assertions.*;

public class AuthScreenStep {
    private AuthScreenController authController;
    private boolean loginSucceeded;

    private static final AtomicBoolean javaFxInitialized = new AtomicBoolean(false);

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXML/authScreen.fxml"));
                Parent root = loader.load();
                this.authController = loader.getController();

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

    @Given("user logs in with initials {string}")
    public void useLogsIn(String initials) throws Exception {
        // Run the login action on the JavaFX application thread
        CountDownLatch loginLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                authController.login(initials);
                
                // Wait for the login to complete
                loginLatch.countDown();
                
                System.out.println("Waiting for login to complete..." + AuthValidation.getCurrentUser());

                // check if it can find a current user in AuthValidation
                loginSucceeded = AuthValidation.getCurrentUser() != null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        loginLatch.await(); // Wait for the login to finish before continuing the test
    }

    @Then("the login should succeed {string}")
    public void theLoginShouldSucceed(String expectedResult) {
        boolean expected = Boolean.parseBoolean(expectedResult);
        assertEquals(expected, loginSucceeded,
                expected ? "Login should have succeeded" : "Login should have failed");
    }

    @Given("user logs in through the login screen")
    public void userLogsInThroughTheLoginScreen() throws Exception {
        // Run the login action on the JavaFX application thread
        CountDownLatch loginLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                authController.handleLoginAction(null);

                // Wait for the login to complete
                loginLatch.countDown();

                // check if it can find a current user in AuthValidation
                loginSucceeded = AuthValidation.getCurrentUser() != null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        loginLatch.await(); // Wait for the login to finish before continuing the test
    }
}