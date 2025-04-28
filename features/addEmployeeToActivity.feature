# Jacob Knudsen (s224372) 

Feature: Add Employee to Project
    Description: an employee is added to a project activity

    # Main scenario
    Scenario: Add employee to project activity
        Given an employee with the ID "huba" exists
        And an activity with the ID "desi" exists
        When the user "nico" adds the employee "huba" to the activity "desi"
        Then the employee "huba" is added to the activity "desi"
