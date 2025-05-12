Feature: Logging time to an activity
  Background: Creating a project with activity
    Given a project in time handler "Project A" exists
    And an activity in time handler "TimeLogTest" exists in "Project A"

  Scenario: Add time to an existing activity
    When I log the time spent in "TimeLogTest" in "Project A"
    Then success check for exception

  Scenario: I add time entry successfull
    When I log the time spent in "admin" date "20.05.2025" and time 2.5
    Then success check for exception

  Scenario: I add time entry with blank initials
    When I log the time spent in " " date "20.05.2025" and time 2.5
    Then check for exception "Initials required."

  Scenario: I add time entry with null initials
    When I log the time spent in null date "20.05.2025" and time 2.5
    Then check for exception "Initials required."

  Scenario: I add time entry with blank date
    When I log the time spent in "admin" date " " and time 2.5
    Then check for exception "Date required."

  Scenario: I add time entry with invalid date
    When I log the time spent in "admin" date null and time 2.5
    Then check for exception "Invalid date format."

  Scenario: I add time entry with invalid time
    When I log the time spent in "admin" date "20.05.2025" and time -4
    Then check for exception "Hours must be > 0"