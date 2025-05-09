Feature: Creating New Projects
  As a user
  I want to create new projects
  So that I can start organizing new work

  Background:
    Given the user "ABC" with role "Developer" is logged in

  Scenario: Creating a new project shows the project creation dialog
    Given the home screen is loaded
    When the user clicks the create project button
    Then the project creation screen should be displayed

  Scenario: Project list is refreshed after creating a new project
    Given the home screen is loaded
    And the project creation screen is displayed
    When the user creates a new project with:
      | projectID | projectName   | memberInitials |
      | P004      | New Project   | ABC,XYZ       |
    And returns to the home screen
    Then the projects list should include "New Project"
    And the projects count label should be updated