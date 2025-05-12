# // [Written by s244706] // #

Feature: Assigning and unassigning employees to an activity

  Background:
    Given a project "Project A" exists
    And an activity "Design" exists in "Project A"
    And a user "Alice" exists

  Scenario: Assign an employee to an activity
    When I assign "Alice" to activity "Design"
    Then "Alice" should be assigned to the activity

  Scenario: Assign the same employee twice
    Given "Alice" is already assigned to "Design"
    When I assign "Alice" to activity "Design"
    Then I should get an error from Assign Activity "Could not assign employee: The employee is already assigned to the activity"

  Scenario: Unassign an employee from an activity
    Given "Alice" is already assigned to "Design"
    When I unassign "Alice" from activity "Design"
    Then "Alice" should not be assigned to the activity

  Scenario: Unassign a user who is not assigned
    When I unassign "Jeff" from activity "Design"
    Then I should get an error from Assign Activity "The employee is not assigned to the activity"

  Scenario: Unassign a nil user from an activity
    When I unassign null from activity "Design"
    Then I should get an error from Assign Activity "Cannot unassign null user."

  Scenario: Unassign a blank user from an activity
    When I unassign " " from activity "Design"
    Then I should get an error from Assign Activity "Cannot unassign null user."

  Scenario: Assign an employee where the activity name is empty
    When I assign "Alice" to activity " "
    Then I should get an error from Assign Activity "Activity with name: ' ' not found."
    When I assign "Alice" to activity null
    Then I should get an error from Assign Activity "Activity with name: 'null' not found."

  Scenario: Assign an employee where the username is incorrect
    When I assign " " to activity "Design"
    Then I should get an error from Assign Activity "Cannot assign null user."
    When I assign null to activity "Design"
    Then I should get an error from Assign Activity "Cannot assign null user."

  Scenario: Check if user is assigned to the activity
    When I check if "Alice" is assigned to activity "Design"
    Then I should get a result of "true"

  Scenario: Check if user is assigned to the activity with blank initials
    When I check if " " is assigned to activity "Design"
    Then I should get a result of "false"

  Scenario: Check if user is assigned to the activity with null initials
    When I check if "null" is assigned to activity "Design"
    Then I should get a result of "false"
    