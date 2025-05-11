Feature: Project Leadership Management
  As a project administrator
  I want to manage project leaders and members
  So that I can track who is responsible for and working on projects

  Background:
    Given a project exists with name "Test Project"
    
  Scenario: Setting project leader initials
    When I set the project leader initials to "JD"
    Then the project leader initials should be "JD"
    
  Scenario: Updating project leader initials
    Given the project leader initials are set to "JD"
    When I update the project leader initials to "MS"
    Then the project leader initials should be "MS"
    
  Scenario: Setting empty project leader initials
    When I set the project leader initials to ""
    Then the project leader initials should be ""
    
  Scenario: Setting null project leader initials
    When I try to set the project leader initials to null
    Then the project leader initials should remain unchanged
    
  Scenario: Retrieving project leader initials
    Given the project leader initials are set to "JD"
    When I retrieve the project leader initials
    Then I should get "JD" as the project leader initials
    
  Scenario: Retrieving member initials when no members exist
    When I retrieve the project member initials
    Then I should get an empty list of member initials
    
  Scenario: Retrieving member initials when members exist
    Given the project has the following members:
      | initials |
      | AB       |
      | CD       |
      | EF       |
    When I retrieve the project member initials
    Then I should get a list containing 3 member initials
    And the list should include the member initials "AB", "CD", and "EF"