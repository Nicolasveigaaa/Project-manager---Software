package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class addEmployeeSteps {
    
    @Given("an employee with the ID {string} exists")
    public void anEmployeeWithTheIDExists(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("an activity with the ID {string} exists")
    public void anActivityWithTheIDExists(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("the user {string} adds the employee {string} to the activity {string}")
    public void theUserAddsTheEmployeeToTheActivity(String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the employee {string} is added to the activity {string}")
    public void theEmployeeIsAddedToTheActivity(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
