Feature: Testing a login
  Users should be able to login to expenses app

  Scenario: Invalid login
    When admin logs in with password invalid
    Then the server should handle it and return a UNAUTHORIZED status

  Scenario: Admin login
    When admin logs in with password admin
    Then the server should handle it and return a OK status
    And auth token should be returned
    And ROLE_ADMIN role should be returned

  Scenario: Manager login
    When manager logs in with password manager
    Then the server should handle it and return a OK status
    And auth token should be returned
    And ROLE_USER_MANAGER role should be returned

  Scenario: User login
    When user1 logs in with password user1
    Then the server should handle it and return a OK status
    And auth token should be returned
    And ROLE_USER role should be returned

  Scenario: New user login
    Given joe signed up with password joe
    When joe logs in with password joe
    Then the server should handle it and return a OK status
    And auth token should be returned
    And ROLE_USER role should be returned
