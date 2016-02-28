package application.knapsack

import solution.knapsack.general.KnapsackExecuter
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 * Created by stefangrecu on 25/02/16.
 */
class Application {
    public static void main(String[] args) {
        KnapsackExecuter executer = new KnapsackExecuter(100)
        //executer.writeTestDataFile(20, 50, 25)
        executer.processData(KnapsackSolutionType.ExtensiveSearch, 0, 'src/main/resources/knapsack/data-20.txt')
        executer.writeResultDataFile()
    }
}