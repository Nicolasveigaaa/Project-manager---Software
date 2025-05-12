# // [Written by s244706] // #

Feature: Starting the main application

  Scenario: Launching the application shows the auth screen
    Given the application is started
    Then the auth screen should be visible