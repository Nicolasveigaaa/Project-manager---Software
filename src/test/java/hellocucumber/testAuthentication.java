package hellocucumber;

import persistence.Database;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.employee.AuthValidation;

public class testAuthentication { 
    private String userId;
    private boolean loginSuccess;

    Database database = new Database();

    AuthValidation auth = new AuthValidation(database); // or mock it if needed

    @Given("a user ID of {string}")
    public void a_user_id_of(String userId) {
        this.userId = userId;
    }

    @When("the user attempts to log in")
    public void the_user_attempts_to_log_in() {
        // Fake a login attempt by simulating input
        loginSuccess = auth.validateLogin(userId); // Use your real or mock password
    }

    @Then("the login should succeed {boolean}")
    public void theAccountBalanceShouldBe(Integer expected) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(expected, loginSuccess);
    }
}
