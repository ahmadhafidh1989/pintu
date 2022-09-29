Feature: API Test

  Scenario: I want to check if data type returned by the API is correct
    When I call get
    Then The API should Return 200 OK
    And The Respons Return Correct DataType

    Scenario: I want to check if the post responses return as the body request
      Given I provide correct body request
      When I Post the body request
      Then The API should Return 201 OK
      And the response should return as body request