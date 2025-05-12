Feature: Editing an activity

  Scenario: User enters valid name and valid time
    Given an activity named "Old" with budgeted time 10.0
    When the user enters "New Activity" as the new name
    And the user enters "20.5" as the new budgeted time
    Then the activity name should be "New Activity"
    And the budgeted time should be 20.5

  Scenario: User leaves name blank
    Given an activity named "Stay" with budgeted time 8.0
    When the user enters "" as the new name
    And the user enters "15.0" as the new budgeted time
    Then the activity name should be "Stay"
    And the budgeted time should be 15.0

  Scenario: User cancels the name dialog
    Given an activity named "Keep" with budgeted time 5.0
    When the user cancels the name input
    And the user enters "10.0" as the new budgeted time
    Then the activity name should be "Keep"
    And the budgeted time should be 10.0

  Scenario: User cancels the time dialog
    Given an activity named "Work" with budgeted time 12.0
    When the user enters "Updated" as the new name
    And the user cancels the time input
    Then the activity name should be "Updated"
    And the budgeted time should be 12.0

  Scenario: User enters invalid time
    Given an activity named "Broken" with budgeted time 7.0
    When the user enters "Fix" as the new name
    And the user enters "not a number" as the new budgeted time
    Then the activity name should be "Fix"
    And the budgeted time should be 7.0
