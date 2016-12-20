Feature: Forgeting map with multiple finds to increase rank

  Scenario: Add an element over the limit to the map and with multiple searches
    Given I put in the map the following data
      | myValue1  | 0    |
      | myValue2  | 5    |
      | myValue3  | 5    |
      | myValue4  | 5    |
      | myValue5  | 5    |
      | myValue6  | 5    |
      | myValue7  | 5    |
      | myValue8  | 5    |
      | myValue9  | 5    |
      | myValue10 | 5    |


    And I search a key with id 0 for 7  times to increase its rank

    And I add to the exiting map the following data
      | myValue11 | 9    |

    When I find a term with id: 0

    Then The expected term "myValue1" is found

