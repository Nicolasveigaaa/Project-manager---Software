Feature: ProjectService

  Background:
    # ensure we start with a clean DB
    Given the database is reset
    And a user with initials "jd" exists

  # 1) addProject + getProjectByID + openProject (present/absent)
  Scenario Outline: addProject and openProject
    When I add a project named "<name>"
    Then I can open the project by ID
    And finding by ID returns present

  Examples:
    | name    |
    | Alpha   |
    | Beta    |

  Scenario: openProject on non-existent ID
    When I open project with ID "no-such"
    Then finding by ID returns empty

  # 2) findProjectByName (present/absent)
  Scenario Outline: findProjectByName
    Given I add a project named "<name>"
    When I search for project by name "<search>"
    Then the result is <present>

    Examples:
      | name    | search  | present |
      | Alpha   | alpha   | true    |
      | Beta    | GAMMA   | false   |

  # 3) createActivityForProject (success, duplicate, missing project)
  Scenario Outline: createActivityForProject
    Given I add a project named "P"
    When I create activity "<act>" on project "P"
    Then creation <outcome>

    Examples:
      | act     | outcome         |
      | Task1   | succeeds        |
      | Task1   | duplicate       |

  Scenario: createActivityForProject on missing project
    When I create activity "X" on project "no-id"
    Then it fails with "Project with ID 'no-id' not found. Cannot create activity."

  # 4) getActivitiesForProject (success, missing)
  Scenario Outline: getActivitiesForProject
    Given I add a project named "P"
    And I create activity "A" on project "P"
    When I list activities for project "P"
    Then I see <count> activities

  Examples:
    | count |
    | 1     |

  Scenario: getActivitiesForProject on missing project
    When I list activities for project "no-id"
    Then it fails with "Project with ID 'no-id' not found."

  # 5) setProjectLeader (success, null initials, user-not-found, project-not-found)
  Scenario Outline: setProjectLeader
    Given I add a project named "P"
    When I set project "P" leader to "<init>"
    Then setting <outcome>

    Examples:
      | init | outcome        |
      | jd   | succeeds       |
      | null | null-error     |
      | xx   | user-not-found |

  Scenario: setProjectLeader on missing project
    When I set project id null with leader to "jd"
    Then it fails with "Project with ID 'null' not found."

  # 6) assignEmployeeToActivity (success, activity-not-found, project-not-found)
  Scenario Outline: assignEmployeeToActivity
    Given I add a project named "P"
    And I create activity "A" on project "P"
    When I assign employee "jd" to activity "A" in project "P"
    Then assignment <outcome>

    Examples:
      | outcome  |
      | succeeds |

  Scenario: assignEmployeeToActivity activity-not-found
    Given I add a project named "P"
    When I assign employee "jd" to activity "X" in project "P"
    Then it fails with "Project with ID 'P' not found."

  Scenario: assignEmployeeToActivity project-not-found
    When I assign employee "jd" to activity "A" in project "no-id"
    Then it fails with "Project with ID 'no-id' not found."

  # 7) logTimeForActivity (success, negative hours, activity-not-found, project-not-found)
  Scenario Outline: logTimeForActivity
    Given I add a project named "P"
    And I create activity "A" on project "P"
    When I log <hours> hours on activity "A" in project "P"
    Then logging <outcome>

    Examples:
      | hours | outcome   |
      | 2.5   | succeeds  |
      | -1.0  | negative-error |

  Scenario: logTimeForActivity activity-not-found
    Given I add a project named "P"
    When I log 1.0 hours on activity "X" in project "P"
    Then it fails with "Project with ID 'P' not found."

  Scenario: logTimeForActivity project-not-found
    When I log 1.0 hours on activity "A" in project "no-id"
    Then it fails with "Project with ID 'no-id' not found."

  # 8) getProjectTimeSummary (success, project-not-found)
  Scenario Outline: getProjectTimeSummary
    Given I add a project named "P"
    And I create activity "A" on project "P"
    And I log 1.0 hours on activity "A" in project "P"
    When I request time summary for project
    Then the summary for "A" is 1.0

  # Scenario: setProject leader on null user
  #   Given I add a project named "P"
  #   When I set project "P" leader to null
  #   Then it fails with "User with initials 'null' not found"

  # Scenario: add project with null name
  #   When I add a project named null
  #   Then it fails with "Project name cannot be null"

  # Scenario: add project with empty name
  #   When I add a project named " "
  #   Then it fails with "Project name cannot be empty"