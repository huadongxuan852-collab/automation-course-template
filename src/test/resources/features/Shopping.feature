Feature: Shopping Cart Test

  Scenario Outline: Add product to cart and delete it
    Given I login with "<username>" and "<password>"
    When I search product "<keyword>" and open "<productName>"
    And I store badge number before adding
    And I add product to cart
    Then I verify product "<productName>" in cart
    And I delete product from cart

    Examples:
      | username                     | password    | keyword | productName               |
      | huadongxuan852@gmail.com     | xuanice123  | merc    | Bơm nước xe Mercedes      |
