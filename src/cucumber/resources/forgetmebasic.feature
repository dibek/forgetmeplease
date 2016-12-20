Feature: Forgeting map basic add and find

  Scenario: Add a list of element and find one of them
    Given I put in the map the following data
      | value     |
      | myValue1  |
      | myValue2  |
      | myValue3  |
      | myValue4  |
      | myValue5  |
      | myValue6  |
      | myValue7  |
      | myValue8  |
      | myValue9  |
      | myValue10 |



    When I find a term with id: 3

    Then The expected term "myValue3" is found


