 // [Written by s244706] //

package hellocucumber.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.TimeOff;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TimeOffStep {
    private TimeOff timeOff;
    private boolean result;

    @Given("a time off period from week {int} to {int} in {int}")
    public void aTimeOffPeriodFromWeekToInYear(int startWeek, int endWeek, int year) {
        timeOff = new TimeOff(startWeek, year, endWeek, year);
    }

    @Given("a time off period from week {int} in {int} to week {int} in {int}")
    public void aTimeOffPeriodFromWeekInYearToWeekInYear(int startWeek, int startYear, int endWeek, int endYear) {
        timeOff = new TimeOff(startWeek, startYear, endWeek, endYear);
    }

    @When("I check if week {int} in {int} overlaps with the time off period")
    public void iCheckIfWeekInYearOverlapsWithTheTimeOffPeriod(int week, int year) {
        result = timeOff.overlaps(week, year);
    }

    @Then("the result should be for TimeLog true")
    public void theResultShouldBeTrue() {
        assertEquals(true, result);
    }

    @Then("the result should be for TimeLog false")
    public void theResultShouldBeFalse() {
        assertEquals(false, result);
    }
}
