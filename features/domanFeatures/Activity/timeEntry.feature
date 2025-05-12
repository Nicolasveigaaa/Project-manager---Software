Feature: Add time entry

    Background:
        Given a project "TimeEntryTest" exists
        When I create an activity "TimeEntryActivity" with budgeted time 20.0, start week 10, start year 2025, end week 20, end year 2025 for "TimeEntryTest"
        When I create an activity " " with budgeted time 20.0, start week 10, start year 2025, end week 20, end year 2025 for "TimeEntryTest"

  Scenario: Add valid time entry
    Given the time logger is initialized
    When I add a time entry with initials "AB", date "2025-05-12", and hours 3.5
    Then the logged time should be 3.5
    And the time log should contain 1 entry

  Scenario: Add time entry with null initials
    Given the time logger is initialized
    When I try to add a time entry with null initials, date "2025-05-12", and hours 2
    Then I should get an error message "Initials required."

  Scenario: Add time entry with blank initials
    Given the time logger is initialized
    When I try to add a time entry with initials "  ", date "2025-05-12", and hours 2
    Then I should get an error message "Initials required."

  Scenario: Add time entry with null date
    Given the time logger is initialized
    When I try to add a time entry with initials "AB", null date, and hours 2
    Then I should get an error message "Date required."

  Scenario: Add time entry with blank date
    Given the time logger is initialized
    When I try to add a time entry with initials "AB", date " ", and hours 2
    Then I should get an error message "Date required."

  Scenario: Add time entry with zero hours
    Given the time logger is initialized
    When I try to add a time entry with initials "AB", date "2025-05-12", and hours 0
    Then I should get an error message "Hours must be > 0"

  Scenario: Add time entry with negative hours
    Given the time logger is initialized
    When I try to add a time entry with initials "AB", date "2025-05-12", and hours -1
    Then I should get an error message "Hours must be > 0"

    Scenario: Check timeEntry from activity
        Given the time logger is initialized
        When I add a time entry with initials "AB", date "2025-05-12", and hours 3.5
        And check for timeEntry from activity "TimeEntryActivity"