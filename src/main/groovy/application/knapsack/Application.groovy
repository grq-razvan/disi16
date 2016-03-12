package application.knapsack

import solution.knapsack.general.ExecutionHandler
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.ExtensiveSearch
import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.RandomSearch

/**
 *  Created by stefangrecu on 25/02/16.
 */
class Application implements RuntimeConstants {

    private ExecutionHandler knapsackExecuter

    private static String generatePath(int number) {
        StringBuilder builder = new StringBuilder()
        builder.append(PATH).append(number).append('.txt')
    }

    Application() {
        this.knapsackExecuter = new ExecutionHandler(0)
    }

    private void generateFiles() {
        ITEM_PARAMS.each { def params ->
            knapsackExecuter.writeTestDataFile(params.itemCount, params.maxItemWeight, params.maxItemValue)
        }
    }

    private void executeClassicMethods() {
        knapsackExecuter.setResultManagerMaxWeight(100)
        CLASSIC_SOLUTIONS.each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.1, generatePath(10))
            knapsackExecuter.writeResultDataFile()
        }

        knapsackExecuter.setResultManagerMaxWeight(50)
        CLASSIC_SOLUTIONS.each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.4, generatePath(15))
            knapsackExecuter.writeResultDataFile()
        }

        knapsackExecuter.setResultManagerMaxWeight(125)
        CLASSIC_SOLUTIONS.each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.9, generatePath(20))
            knapsackExecuter.writeResultDataFile()
        }

        knapsackExecuter.setResultManagerMaxWeight(250)
        (CLASSIC_SOLUTIONS - ExtensiveSearch).each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.01, generatePath(50))
            knapsackExecuter.writeResultDataFile()
        }

        knapsackExecuter.setResultManagerMaxWeight(750)
        (CLASSIC_SOLUTIONS - ExtensiveSearch).each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.5, generatePath(100))
            knapsackExecuter.writeResultDataFile()
        }

        knapsackExecuter.setResultManagerMaxWeight(1500)
        (CLASSIC_SOLUTIONS - ExtensiveSearch).each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.7, generatePath(200))
            knapsackExecuter.writeResultDataFile()
        }

        knapsackExecuter.setResultManagerMaxWeight(10000)
        (CLASSIC_SOLUTIONS - ExtensiveSearch).each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.08, generatePath(1000))
            knapsackExecuter.writeResultDataFile()
        }

        knapsackExecuter.setResultManagerMaxWeight(30000)
        (CLASSIC_SOLUTIONS - ExtensiveSearch).each { KnapsackSolutionType type ->
            knapsackExecuter.processData(type,
                    type != RandomSearch ? 0.0 : 0.8, generatePath(50000))
            knapsackExecuter.writeResultDataFile()
        }
    }

    private void executeHillClimbing() {

    }

    public static void main(String[] args) {
        Application application = new Application()
        application.generateFiles()
        application.executeClassicMethods()
        application.executeHillClimbing()
    }
}