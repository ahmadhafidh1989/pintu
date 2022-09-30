Feature: Register Test

  Scenario: Register With Invalid email
    Given I want to register with invalid email address
    When I register
    Then I should get registration error message saying 'Enter Valid Email'

  Scenario: Register With out providing name
    Given I want to register with out providing name
    When I register
    Then I should get registration error message saying 'Enter Full Name'

  Scenario: Register With out providing password
    Given I want to register with out providing password
    When I register
    Then I should get registration error message saying 'Enter Password'

  Scenario: Register With invalid confirm password
    Given I want to register With invalid confirm password
    When I register
    Then I should get registration error message saying 'Password Does Not Matches'

  Scenario: Register With valid login info
    When I register
    Then I should be able to login
    And I should logged in