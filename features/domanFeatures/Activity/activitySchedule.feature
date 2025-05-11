# // [Written by s244706] // #

Feature: Activity schedule

  Scenario: Check if an activity is active in a given week and year
    Given an activity from week 10 of 2023 to week 20 of 2024
    When I check if the activity is active in week 15 of 2023
    Then the result should be true

  Scenario: Check if an activity is not active before start year
    Given an activity from week 10 of 2023 to week 20 of 2024
    When I check if the activity is active in week 5 of 2022
    Then the result should be false

  Scenario: Check if an activity is not active after end year
    Given an activity from week 10 of 2023 to week 20 of 2024
    When I check if the activity is active in week 30 of 2025
    Then the result should be false

  Scenario: Check if an activity is not active before start week
    Given an activity from week 10 of 2023 to week 20 of 2024
    When I check if the activity is active in week 5 of 2023
    Then the result should be false

  Scenario: Check if an activity is not active after end week
    Given an activity from week 10 of 2023 to week 20 of 2024
    When I check if the activity is active in week 25 of 2023
    Then the result should be false

  Scenario: Check if an activity is active in the same week and year
    Given an activity from week 10 of 2023 to week 20 of 2024
    When I check if the activity is active in week 10 of 2023
    Then the result should be true

  Scenario: Check if an activity is active in the same week and year
    Given an activity from week 10 of 2023 to week 20 of 2024
    When I check if the activity is active in week 20 of 2024
    Then the result should be true