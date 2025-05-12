Feature: Add Activity to project

    Scenario:A
        Given a projectID "pid"
        And a activity name "act"
        And a budgeted time of 100
        And the project exist
        And the activity name is unique
        When the activity is created
        Then the activity is created Successfully

    Scenario:B
        Given a projectID "pid"
        And a activity name "act"
        And a budgeted time of 100
        And the project does not exist
        When the activity is created
        Then it fails with "Project with ID 'pid' not found. Cannot create activity."
    
    Scenario:C
        Given a projectID "pid"
        And a activity name ""
        And a budgeted time of 100
        And the project exist
        And the activity name is unique
        When the activity is created
        Then it fails with "Activity name cannot be empty."
    
    Scenario:D
        Given a projectID "pid"
        And a activity name "act"
        And a budgeted time of 100
        And the project exist
        And the activity name already exist in project
        When the activity is created
        Then it fails with "Activity with name 'act' already exists in project. Cannot create activity."

    Scenario:E
        Given a projectID "pid"
        And a activity name "act"
        And a budgeted time of -20
        And the project exist
        And the activity name is unique
        When the activity is created
        Then it fails with "Budgeted time cannot be negative."