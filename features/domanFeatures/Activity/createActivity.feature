# // [Written by s244706] // #

Feature: Creating an activity

  Scenario: Successfully create an activity with valid parameters
    Given a project "ProjectA-001" exists
    When I create an activity "Design" with budgeted time 20.0, start week 10, start year 2025, end week 20, end year 2025 for "ProjectA-001"
    Then I check if everythign is set up correctly
    Then the activity "Design" should be created successfully
    And its budgeted time should be 20.0
    And it should belong to "ProjectA-001"

  Scenario: Fail to create an activity without a project
    When I try to create an activity "Design" without a project
    Then I should get an error "Activity must belong to a project."

  Scenario: Fail to create an activity with empty name
    Given a project "ProjectA-001" exists
    When I try to create an activity with an empty name for ""
    Then I should get an error "Activity name cannot be empty."

  Scenario: Fail to create an activity with negative budgeted time
    Given a project "ProjectA-001" exists
    When I try to create an activity "Design" with budgeted time -5.0
    Then I should get an error "Budgeted time cannot be negative."

  Scenario: Changing the budgeted time of an activity to negative value
    Given a project "ProjectA-001" exists
    And an activity "Design" with budgeted time 20.0 exists in "ProjectA-001"
    When I change the budgeted time of activity "Design" to -1.0
    Then I should get an error "Budgeted time cannot be negative."