package application.knapsack

import solution.knapsack.general.KnapsackExecuter
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 25/02/16.
 */
class Application {
    public static void main(String[] args) {
        def ke = new KnapsackExecuter(100, 0.1)
        ke.processData(KnapsackSolutionType.RandomSearch, 0.1, "src/main/resources/knapsack/data-20.txt")
        ke.writeResultDataFile()
    }
}