# // [Written by s244706] // #

# Feature: Assigning an employee to an activity

#   Scenario: User enters valid initials
#     Given an activity
#     When the user enters "huba"
#     Then the employee "huba" should be assigned to the activity

#   Scenario: User enters invalid initials
#     Given an activity that rejects assignments
#     When the user enters "fail"
#     Then an error should be logged

#   Scenario: User cancels the dialog
#     Given an activity
#     When the user cancels the dialog
#     Then no employee should be assigned
