Feature: RoleChecker

  #─────────────────────────────────────────────────────────────────────────────#
  # Scenario Outline for isManager()
  Scenario Outline: isManager should only return true for “manager”
    Given a role "<role>"
    When I call isManager
    Then the result is <manager>

    Examples:
      | role      | manager |
      | manager   | true    |
      | MANAGER   | true    |
      | employee  | false   |
      | random    | false   |

  #─────────────────────────────────────────────────────────────────────────────#
  # Scenario Outline for isEmployee()
  Scenario Outline: isEmployee should only return true for “employee”
    Given a role "<role>"
    When I call isEmployee
    Then the result is <employee>

    Examples:
      | role      | employee |
      | employee  | true     |
      | EMPLOYEE  | true     |
      | manager   | false    |
      | random    | false    |

  #─────────────────────────────────────────────────────────────────────────────#
  # Scenario Outline for isValidRole()
  Scenario Outline: isValidRole should return true for either valid role
    Given a role "<role>"
    When I call isValidRole
    Then the result is <valid>

    Examples:
      | role      | valid |
      | manager   | true  |
      | MANAGER   | true  |
      | employee  | true  |
      | EMPLOYEE  | true  |
      | random    | false |