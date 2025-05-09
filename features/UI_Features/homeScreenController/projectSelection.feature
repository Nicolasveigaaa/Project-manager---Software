Feature: Project Selection and Opening
  As a user
  I want to select and open projects
  So that I can view or edit project details

  Background:
    Given the user "ABC" with role "Developer" is logged in
    And the database contains the following projects:
      | projectID | projectName   | memberInitials |
      | P001      | Test Project  | ABC,XYZ        |
      | P002      | Demo Project  | DEF,GHI        |
      | P003      | Alpha Project | ABC,DEF        |

  Scenario: Open button is enabled when a project is selected
    Given the home screen is loaded
    When the user selects the project "Test Project" from the list
    Then the open project button should be enabled

  Scenario: Opening a selected project navigates to project screen
    Given the home screen is loaded
    And the user has selected the project "Test Project" from the list
    When the user clicks the open project button
    Then the project service should open project with ID "P001"
    And the user should be redirected to the project screen
    And the project screen should display "Test Project" details
