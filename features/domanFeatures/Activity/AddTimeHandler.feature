Feature: Logging time to an activity

  Scenario: Valid input and user is assigned
    Given a mock activity with user "huba" assigned
    And current user is "huba"
    When the user inputs "2.0"
    Then 2.0 hours should be logged

  Scenario: Valid input and user is not assigned
    Given a mock activity with no users assigned
    And current user is "huba"
    When the user inputs "2.0"
    Then an error should be shown about assignment
    And 0.0 hours should be logged

  Scenario: Invalid input (not a number)
    Given a mock activity with user "huba" assigned
    And current user is "huba"
    When the user inputs "two hours"
    Then an error should be shown about invalid number
    And 0.0 hours should be logged

  Scenario: Exception thrown during time entry
    Given a mock activity that throws error when logging time
    And current user is "huba"
    When the user inputs "2.0"
    Then an error should be shown with message "test error"
    And 0.0 hours should be logged

  Scenario: User cancels the input dialog
    Given a mock activity with user "huba" assigned
    When the user cancels the dialog
    Then 0.0 hours should be logged
