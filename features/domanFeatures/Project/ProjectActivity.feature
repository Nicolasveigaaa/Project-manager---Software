# // [Written by s244706] // #

Feature: Project Activity Management
  As a project manager
  I want to manage activities within a project
  So that I can organize and track project tasks effectively

  Background:
    Given a project exists with name "Test Project"

  Scenario: Successfully adding an activity to a project
    When I add an activity named "Task 1" to the project
    Then the activity should be successfully added to the project
    And the project should have 1 activity

  Scenario: Retrieving an activity by name
    Given the project has an activity named "Task 1"
    When I retrieve the activity with name "Task 1"
    Then I should get the correct activity with name "Task 1"

  Scenario: Retrieving all activities in a project
    Given the project has the following activities:
      | name   | description |
      | Task 1 | First task  |
      | Task 2 | Second task |
      | Task 3 | Third task  |
    When I request all activities
    Then I should get a list containing 3 activities
    And the list should contain activities with names "Task 1", "Task 2", and "Task 3"

  Scenario: Attempting to add a null activity
    When I try to add a null activity to the project
    Then an IllegalArgumentException should be thrown with message "Cannot add a null activity."

  # Scenario: Attempting to add an activity that belongs to a different project
  #   Given a project exists with name "Other Project"
  #   And an activity named "Foreign Task" belonging to "Other Project" exists
  #   When I try to add the activity "Foreign Task" to the project "Test Project"
  #   Then an IllegalArgumentException should be thrown with message "Activity belongs to a different project. Cannot add."

  Scenario: Attempting to add an activity with a duplicate name
    Given the project has an activity named "Task 1"
    When I try to add another activity with name "Task 1"
    Then an IllegalArgumentException should be thrown with message "Activity with name 'Task 1' already exists in project. Cannot create activity."

  Scenario: Retrieving a non-existent activity
    When I retrieve the activity with name "Non-existent"
    Then I should get a null result

  Scenario: Retrieving all acitivities
    When I retrieve all activities
    Then I should get a list of all acitivities

  # Scenario: Add Acitivity does not belong to the project
  #   When I add an activity named "Task 3" to another project
  #   Then an IllegalArgumentException should be thrown with message "Activity belongs to a different project. Cannot add."

  Scenario: Add activity that already exists
    When I add an activity named "Task 3" to the project
    When I add an activity named "Task 3" to the project
    Then an IllegalArgumentException should be thrown with message "Activity with name 'Task 3' already exists in project. Cannot create activity."

# Feature: Project Activity Management
#     As a project manager
#   I want to manage activities within a project
#   So that I can organize and track project tasks effectively

#   Background:
#     Given a project exists with name "Test Project"

#     Scenario: Successfully adding an activity to a project
#       When I add an activity named "Task 1" to the project
#       Then I should get a null result

#     Scenario: Activity name null
#       When I add an activity named null to the project
#       Then an IllegalArgumentException should be thrown with message "Cannot add a null activity."

#     Scenario: Activity is not part of the project
#       When I add an activity named " " to the project
#       Then an IllegalArgumentException should be thrown with message "Activity name cannot be empty."

#     Scenario: Activity is not already part of the project
#       When I add an activity named " " to the project
#       Then an IllegalArgumentException should be thrown with message "Activity name cannot be empty."