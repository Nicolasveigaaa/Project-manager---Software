Feature: Home Screen Display
  As a user
  I want to see my project list when I access the home screen
  So that I can view and manage my projects

  Background:
    Given the user "ABC" with role "Developer" is logged in
    And the database contains the following projects:
      | projectID | projectName   | memberInitials |
      | P001      | Test Project  | admin,nico        |
      | P002      | Demo Project  | DEF,GHI        |
      | P003      | Alpha Project | ABC,DEF        |

  Scenario: User sees their assigned projects on home screen
    When the home screen loads
    Then the user should see 2 projects in the list
    And the projects list should contain "Test Project" and "Alpha Project"
    And the projects count label should display "2"
    And the open project button should be disabled

  Scenario: User information is displayed correctly
    When the home screen loads
    Then the initials label should display "admin"
    And the role label should display "Developer"

