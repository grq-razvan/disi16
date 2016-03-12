package application.knapsack

import solution.knapsack.general.ExecutionHandler
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 12/03/16.
 */
class ExecutionDataHolder implements RuntimeConstants {

    private final ExecutionHandler handler

    ExecutionDataHolder() {
        this.handler = new ExecutionHandler(0)
    }

    private static String generatePath(int number) {
        StringBuilder builder = new StringBuilder()
        builder.append(PATH).append(number).append('.txt')
    }

    void generateFiles() {
        ITEM_PARAMS.each { def params ->
            handler.writeTestDataFile(params.itemCount, params.maxItemWeight, params.maxItemValue)
        }
    }

    private void executeTestInternal(Integer knapsackWeight, Integer itemCount, String type, Double certainty) {
        List<KnapsackSolutionType> availableTypes = []
        setAvailableTypes(itemCount, type, availableTypes)
        handler.setResultManagerMaxWeight(knapsackWeight)
        availableTypes.each {
            handler.processData(it, certainty, generatePath(itemCount))
        }
    }

    void executeTest(Map<String, Object> params) {
        Integer weight = params.knapsackWeight as Integer
        Integer itemCount = params.itemCount as Integer
        String type = params.type as String
        Double certainty = params.certainty as Double
        executeTestInternal(weight, itemCount, type, certainty)
    }

    void executeClassicMethodsBatchTest() {
        CLASSIC_TEST_CASES.each {
            executeTest(it)
        }
    }

    private static void setAvailableTypes(Integer count, String type, List<KnapsackSolutionType> availableTypes) {
        if (type == CLASSIC) {
            if (count > 20) {
                availableTypes += CLASSIC_SOLUTIONS
            } else {
                availableTypes += CLASSIC_SOLUTIONS
                availableTypes -= KnapsackSolutionType.Stochastic
            }
        } else {
            availableTypes += HILL_CLIMBING_SOLUTIONS
        }
    }

}
