Feature: Login to the application
  As a user
  I want to log in with my initials
  So that I can access the application

  Scenario: Successful login to the application
    Given user logs in with initials "admin"
    Then the login should succeed "true"

  Scenario: Failed login with invalid initials
    Given user logs in with initials "invalid"
    Then the login should succeed "false"

  Scenario: User logs in trough the login screen
    Given user logs in through the login screen
    Then the login should succeed "false"

  Scenario Outline: Multiple login attempts
    Given user logs in with initials <initials>
    Then the login should succeed <success>

    Examples:
      | initials | success |
      | "admin"  | "true"  |
      | "huba"   | "true"  |
      | "invalid"| "false" |
      | ""       | "false" |
