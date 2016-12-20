Feature: Forgeting map basic add and find over limit

  Scenario: Add an element over the limit to the map
    Given I put in the map the following data
      | myValue1  | 5    |
      | myValue2  | 5    |
      | myValue3  | 5    |
      | myValue4  | 5    |
      | myValue5  | 5    |
      | myValue6  | 5    |
      | myValue7  | 5    |
      | myValue8  | 5    |
      | myValue9  | 5    |
      | myValue10 | 5    |
      | myValue11 | 5    |


    When I find a term with id: 0

    Then The expected term "NotFound" is found