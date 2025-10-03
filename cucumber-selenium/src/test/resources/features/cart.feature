Feature: Cart functionality

  Scenario Outline: Add product to cart and open cart page
    Given I am logged in
    When I add product "<product>" to cart
    And I click "Cart" button
    Then Verify that cart page is displayed

    Examples:
      | product    |
      | Blue Top   |
      | Men Tshirt |