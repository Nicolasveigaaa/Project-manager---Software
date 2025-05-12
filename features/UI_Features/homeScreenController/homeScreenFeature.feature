# // [Written by s244706] // #

# Feature: Home Screen Functionality
#   As a user
#   I want to interact with the home screen
#   So that I can manage my projects

#   Background:
#     Given I am logged in as user "ABC" with role "developer"
#     And I am on the home screen

#   Scenario: User information is displayed correctly
#     Then I should see my initials "ABC" displayed
#     And I should see my role "developer" displayed

#   Scenario: Projects are listed and counted
#     Then I should see the correct number of projects in the count label
#     And the projects list should contain only my projects

#   Scenario: Creating a new project
#     When I click the create project button
#     Then the project creation screen should open
#     And the projects list should be refreshed

#   Scenario: Opening a project
#     When I select a project from the list
#     And I click the open project button
#     Then the selected project should be opened

#   Scenario: Logging out
#     When I click the logout button
#     Then I should be redirected to the login screen