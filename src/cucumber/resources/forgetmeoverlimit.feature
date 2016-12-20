Feature: Forgeting map basic add and find over limit

  Scenario: Add an element over the limit to the map
    Given I put in the map the following data
      | value     | id | rank |
      | myValue1  | 1  | 0    |
      | myValue2  | 2  | 5    |
      | myValue3  | 3  | 5    |
      | myValue4  | 4  | 5    |
      | myValue5  | 5  | 5    |
      | myValue6  | 6  | 5    |
      | myValue7  | 7  | 5    |
      | myValue8  | 8  | 5    |
      | myValue9  | 9  | 5    |
      | myValue10 | 10 | 5    |
      | myValue10 | 11 | 5    |


    When I find a term with id: 1

    Then The expected term "NotFound" is found