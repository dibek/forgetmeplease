Feature: Forgeting map basic add and find over limit

  Scenario: Add an element over the limit to the map
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
      | myValue11 |


    When I find a term with id: 1

    Then The expected term "NotFound" is found