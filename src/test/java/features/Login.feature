Feature: LoginFeature
  This feature tests the login functionality of the application using valid and invalid credentials.

  Scenario: Login with valid credentials
    Given I navigate to the login page
    And I enter the following for Login
      | username | password     |
      | student  | Password123  |
    And I click login button
    Then I should see the success page

  Scenario: Login with invalid credentials
    Given I navigate to the login page
    And I enter the following for Login
      | username  | password   |
      | wrongUser | wrongPass |
    And I click login button
    Then I should not see the success page
