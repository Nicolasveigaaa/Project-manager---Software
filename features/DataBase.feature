Feature: Check Database persistence
  As a user
  I want to be able to check if the database is still available
  And check if the database is accessible for specific things

  Background:
    Given the database is reset
    Then create new database

  Scenario: Check if the database is still available
    Given the database is not empty
    Then the database should be available

  Scenario: Fail, add user to non existing project
    Given the database is not empty
    When I add a user "Jeff" to a non existing project "no-id"
    Then it should throw an exception "Project with ID 'no-id' not found. Cannot add user 'Jeff'."

  Scenario: Success, add user to existing project
    Given the database is not empty
    And a project "ProjectA-001" in database exists
    When I add a user "Jeff" to project the existing project
    Then the user "Jeff" should be added to the project

  Scenario: Get all users
    Given the database is not empty
    When I get all users
    Then the list should contain the at least 4 users

  Scenario: Reset database
    Given the database is not empty
    When I reset the database
    Then the database should be empty
