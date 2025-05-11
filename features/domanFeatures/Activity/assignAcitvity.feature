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
    Then I should get an error "The employee is already assigned to the activity"

  Scenario: Unassign an employee from an activity
    Given "Alice" is already assigned to "Design"
    When I unassign "Alice" from activity "Design"
    Then "Alice" should not be assigned to the activity

  Scenario: Unassign a user who is not assigned
    When I unassign "Alice" from activity "Design"
    Then I should get an error "The employee is not assigned to the activity"
