# // [Written by s244706] // #

Feature: Time Off Period Overlapping
As a project manager
I want to check if a given week overlaps with a time off period
So that I can plan resources accordingly

  Scenario: Week falls within same year time off period
    Given a time off period from week 20 to 30 in 2025
    When I check if week 25 in 2025 overlaps with the time off period
    Then the result should be for TimeLog true

  Scenario: Week falls outside same year time off period
    Given a time off period from week 20 to 30 in 2025
    When I check if week 15 in 2025 overlaps with the time off period
    Then the result should be for TimeLog false

  Scenario: Week falls within multi-year time off period
    Given a time off period from week 48 in 2025 to week 5 in 2026
    When I check if week 2 in 2026 overlaps with the time off period
    Then the result should be for TimeLog true

  Scenario: Week is in start year of multi-year period
    Given a time off period from week 48 in 2025 to week 5 in 2026
    When I check if week 50 in 2025 overlaps with the time off period
    Then the result should be for TimeLog true

  Scenario: Week is in end year of multi-year period
    Given a time off period from week 48 in 2025 to week 5 in 2026
    When I check if week 3 in 2026 overlaps with the time off period
    Then the result should be for TimeLog true

  Scenario: Week is before multi-year period
    Given a time off period from week 48 in 2025 to week 5 in 2026
    When I check if week 47 in 2025 overlaps with the time off period
    Then the result should be for TimeLog false

  Scenario: Week is after multi-year period
    Given a time off period from week 48 in 2025 to week 5 in 2026
    When I check if week 6 in 2026 overlaps with the time off period
    Then the result should be for TimeLog false

  Scenario: Year is completely outside time off period
    Given a time off period from week 48 in 2025 to week 5 in 2026
    When I check if week 30 in 2027 overlaps with the time off period
    Then the result should be for TimeLog false

  Scenario: Year is in the middle of a different multi-year time off period
    Given a time off period from week 48 in 2025 to week 5 in 2028
    When I check if week 10 in 2027 overlaps with the time off period
    Then the result should be for TimeLog true

  Scenario: Year is before the time off period's start year
    Given a time off period from week 10 in 2025 to week 20 in 2027
    When I check if week 15 in 2024 overlaps with the time off period
    Then the result should be for TimeLog false

  Scenario: Year is after the time off period's end year
    Given a time off period from week 10 in 2025 to week 20 in 2027
    When I check if week 15 in 2028 overlaps with the time off period
    Then the result should be for TimeLog false

  Scenario: Year == endyear
    Given a time off period from week 10 in 2025 to week 20 in 2026
    When I check if week 20 in 2026 overlaps with the time off period
    Then the result should be for TimeLog true
