Feature: Login Test

    Scenario: Login With Invalid email format
      Given I want to login with invalid email format
      When I Log in
      Then I should get login error invalid email format message

    Scenario: Login Without password
      Given I want to login without providing password
      When I Log in
      Then I should get login error invalid email format message

    Scenario: Login with invalid credential
      Given I want to login with invalid password or email
      When I Log in
      Then I should get login error invalid error or password

    Scenario: Login Succesful
      Given I want to login with valid email and password
      When I Log in
      Then I should redirect to home page