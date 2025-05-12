# // [Written by s244706] // #

Feature: Logging time on an activity

  Scenario: Log time successfully
    Given I reset the logged time to 0.0
    When I log 3.5 hours to activity "Development"
    Then the logged time of activity "Development" should be 3.5

  Scenario: Log multiple times
    Given I reset the logged time to 0.0
    When I log 2.0 hours to activity "Development"
    And I log 1.5 hours to activity "Development"
    Then the logged time of activity "Development" should be 3.5

  Scenario: Fail to log non-positive time
    When I log -1.0 hours to activity "Development"
    Then I should get an error from log time "Could not log time: Logged time must be positive."

  Scenario: Setting logged time to negative
    Given I reset the logged time to -1.0
    Then I should get an error from log time "Logged time cannot be negative."