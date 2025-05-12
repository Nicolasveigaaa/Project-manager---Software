package hellocucumber.domain.activities;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import app.project.ProjectService;
import domain.Activity;
import domain.Project;

import java.util.ArrayList;

public class timeLogger {
    private Activity activity;
    private Exception caughtException;
    private double loggedHours;
    private int logSize;

    ProjectService projectService = new ProjectService();

    @Given("the time logger is initialized")
    public void the_time_logger_is_initialized() {
        // Get project
        Project project = projectService.findProjectByName("TimeEntryTest").get();
        this.activity = project.getActivityByName("TimeEntryActivity");
        caughtException = null;
    }

    @When("I add a time entry with initials {string}, date {string}, and hours {double}")
    public void i_add_a_time_entry_with_valid_input(String initials, String date, double hours) {
        activity.addTimeEntry(initials, date, hours);
        loggedHours = activity.getLoggedTime();
    }

    @When("I try to add a time entry with null initials, date {string}, and hours {double}")
    public void i_add_entry_with_null_initials(String date, double hours) {
        try {
            activity.addTimeEntry(null, date, hours);
            loggedHours = activity.getLoggedTime();
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @When("I try to add a time entry with initials {string}, date {string}, and hours {double}")
    public void i_add_entry_with_invalid_input(String initials, String date, double hours) {
        try {
            activity.addTimeEntry(initials, date, hours);
            loggedHours = activity.getLoggedTime();
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @When("I try to add a time entry with initials {string}, null date, and hours {double}")
    public void i_add_entry_with_null_date(String initials, double hours) {
        try {
            activity.addTimeEntry(initials, null, hours);
            loggedHours = activity.getLoggedTime();
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @And("check for timeEntry from activity {string}")
    public void check_for_timeEntry_from_activity(String activityName) {
        List<Activity.TimeEntry> timeEntries = activity.getTimeLog();
        for (Activity.TimeEntry entry : timeEntries) {
            System.out.println(entry.getDate() + " " + entry.getHours() + " " + entry.getUserInitials() + " " );
            System.out.println(entry.hoursProperty().get() + " " + entry.userInitialsProperty().get() + " " + entry.dateProperty().get());
        }
    }

    @Then("the logged time should be {double}")
    public void the_logged_time_should_be(double expectedHours) {
        assertEquals(expectedHours, loggedHours, 0.0001);
    }

    @Then("the time log should contain {int} entry")
    public void the_time_log_should_contain_entry(int expectedSize) {
        assertEquals(expectedSize, activity.getTimeLog().size());
    }

    @Then("I should get an error message {string}")
    public void i_should_get_an_error_message(String expectedMessage) {
        assertNotNull(caughtException);
        assertEquals(expectedMessage, caughtException.getMessage());
    }

    // Mocked classes for testing
    static class TimeLogger {
        private double loggedTime = 0;
        private List<TimeEntry> timeLog = new ArrayList<>();

        public void addTimeEntry(String initials, String date, double hours) {
            if (initials == null || initials.isBlank()) throw new IllegalArgumentException("Initials required.");
            if (date == null || date.isBlank()) throw new IllegalArgumentException("Date required.");
            if (hours <= 0) throw new IllegalArgumentException("Hours must be > 0");
            this.loggedTime += hours;
            this.timeLog.add(new TimeEntry(initials, date, hours));
        }

        public double getLoggedTime() {
            return loggedTime;
        }

        public List<TimeEntry> getTimeLog() {
            return timeLog;
        }
    }

    static class TimeEntry {
        private final String initials;
        private final String date;
        private final double hours;

        public TimeEntry(String initials, String date, double hours) {
            this.initials = initials;
            this.date = date;
            this.hours = hours;
        }
    }
}
