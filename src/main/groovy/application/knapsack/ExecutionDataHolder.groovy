package application.knapsack

import solution.knapsack.general.ExecutionHandler
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 12/03/16.
 */
class ExecutionDataHolder implements RuntimeConfiguration {

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

    private void executeTestInternal(Integer knapsackWeight, Integer itemCount, String type, Double certainty, Integer regions = 0, Integer degree = 0) {
        List<KnapsackSolutionType> availableTypes = []
        setAvailableTypes(itemCount, type, availableTypes)
        handler.createResultHandlerWith(knapsackWeight)
        availableTypes.each {
            handler.processData(it, certainty, generatePath(itemCount), regions, degree)
            handler.writeResultDataFile()
        }
    }

    void executeTest(Map<String, Object> params) {
        Integer weight = params.knapsackWeight as Integer
        Integer itemCount = params.itemCount as Integer
        String type = params.type as String
        Double certainty = params.certainty as Double
        Integer numberOfRegions = params.regions as Integer ?: 0
        Integer degrees = params.degree as Integer ?: 0
        executeTestInternal(weight, itemCount, type, certainty, numberOfRegions, degrees)
    }


    void executeClassicMethodsBatchTest() {
        CLASSIC_TEST_CASES.each {
            executeTest(it)
        }
    }

    void executeHillClimbingBatchTest() {
        HILL_CLIMBING_TEST_CASES.each {
            executeTest(it)
        }
    }

    private static void setAvailableTypes(Integer count, String type, List<KnapsackSolutionType> availableTypes) {
        if (type == CLASSIC) {
            if (count < 21) {
                CLASSIC_SOLUTIONS.each { availableTypes.add(it) }
            } else {
                CLASSIC_SOLUTIONS.each {
                    if (it != KnapsackSolutionType.Exhaustive) {
                        availableTypes.add(it)
                    }
                }
            }
        } else {
            HILL_CLIMBING_SOLUTIONS.each {
                availableTypes.add(it)
            }
        }
    }

}
