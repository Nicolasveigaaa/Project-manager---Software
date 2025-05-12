package hellocucumber.employees;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class RoleCheckerSteps {
    private String role;
    private boolean result;

    @Given("a role {string}")
    public void a_role(String role) {
        this.role = role;
    }

    @When("I call isManager")
    public void i_call_is_manager() {
        result = RoleChecker.isManager(role);
    }

    @When("I call isEmployee")
    public void i_call_is_employee() {
        result = RoleChecker.isEmployee(role);
    }

    @When("I call isValidRole")
    public void i_call_is_valid_role() {
        result = RoleChecker.isValidRole(role);
    }

    @Then("the result is {string}")
    public void the_result_is(String expectedResult) {
        boolean expected = Boolean.parseBoolean(expectedResult);
        assertEquals(expected, result);
    }
}