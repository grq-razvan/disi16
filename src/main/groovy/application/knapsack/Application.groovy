package application.knapsack

import solution.knapsack.general.KnapsackExecuter

/**
 * Created by stefangrecu on 25/02/16.
 */
class Application {
    public static void main(String[] args) {
        KnapsackExecuter executer = new KnapsackExecuter()
        executer.writeTestDataFile(10)
        executer.processData()
        executer.writeResultDataFile()
    }
}
