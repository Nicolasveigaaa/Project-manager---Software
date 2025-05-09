Feature: User Logout
  As a user
  I want to log out from the application
  So that I can secure my account when I'm done

  Background:
    Given the user "ABC" with role "Developer" is logged in

  Scenario: User logs out and returns to login screen
    Given the home screen is loaded
    When the user clicks the logout button
    Then the user should be redirected to the login screen