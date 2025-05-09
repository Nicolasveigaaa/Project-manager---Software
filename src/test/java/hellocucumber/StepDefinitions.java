package hellocucumber;

import io.cucumber.java.en.Given;

public class StepDefinitions {
    @Given("I say hello")
    public void i_say_hello() {
        System.out.println("Hello, Cucumber!");
    }
}
