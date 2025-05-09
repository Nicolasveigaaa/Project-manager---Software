Feature: Logging time on an activity

  Background:
    Given a project "Project A" exists
    And an activity "Development" with budgeted time 30.0 exists in "Project A"

  Scenario: Log time successfully
    When I log 3.5 hours to activity "Development"
    Then the logged time of activity "Development" should be 3.5

  Scenario: Log multiple times
    When I log 2.0 hours to activity "Development"
    And I log 1.5 hours to activity "Development"
    Then the logged time of activity "Development" should be 3.5

  Scenario: Fail to log non-positive time
    When I log -1.0 hours to activity "Development"
    Then I should get an error "Logged time must be positive."
