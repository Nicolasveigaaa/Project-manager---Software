Feature: Logging in to the application

  Scenario: Successful login to the application
    Given a user ID of "<userID>"
    When the user attemptss to log in
    Then the login should succeed "true"

  Examples:
    | userID   |
    | user123  |
    | testUser |
    | abcXYZ   |