Feature: Login Functionality

  Background: Open site and go to login
    Given Launch browser
    When I navigate to url "http://automationexercise.com"
    Then Verify that home page is visible successfully
    And I click "Signup / Login" button

  Scenario Outline: Login attempt using credentials defined in the feature
    When I fill email "<email>" and password "<password>" and click "Login" button
    Then I should see "<expected_message>"

    Examples:
      | case  | email                 | password    | expected_message                 |
      | valid | tesths@gmail.com      | tesths      | Logged in as test                |