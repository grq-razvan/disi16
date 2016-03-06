package application.knapsack

import solution.knapsack.general.KnapsackExecuter
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 25/02/16.
 */
class Application {

    private static final String PATH = 'src/main/resources/knapsack/data-'

    private KnapsackExecuter knapsackExecuter

    private static String generatePath(int number) {
        StringBuilder builder = new StringBuilder()
        builder.append(PATH).append(number).append('.txt')
    }

    private void generateFiles() {
        knapsackExecuter = new KnapsackExecuter(0, 0.0)
        knapsackExecuter.writeTestDataFile(10, 50, 30)
        knapsackExecuter.writeTestDataFile(15, 25, 10)
        knapsackExecuter.writeTestDataFile(30, 60, 100)
        knapsackExecuter.writeTestDataFile(100, 150, 250)
        knapsackExecuter.writeTestDataFile(50, 10, 130)
        knapsackExecuter.writeTestDataFile(200, 250, 125)
        knapsackExecuter.writeTestDataFile(1000, 150, 150)
        knapsackExecuter.writeTestDataFile(50000, 250, 175)
    }

    private void executeSmallNumberTestCases() {
        //100kg, item wmax = 50, vmax=30
        knapsackExecuter = new KnapsackExecuter(100, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.ExtensiveSearch, 0.0, generatePath(10))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(10))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.1, generatePath(10))
        knapsackExecuter.writeResultDataFile()

        //50 kg, item wmax = 25, vmax=10
        knapsackExecuter = new KnapsackExecuter(50, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.ExtensiveSearch, 0.0, generatePath(15))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(15))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.4, generatePath(15))
        knapsackExecuter.writeResultDataFile()

        //125 kg, wmax = 50, vmax = 25 or more, forgot..
        knapsackExecuter = new KnapsackExecuter(125, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.ExtensiveSearch, 0.0, generatePath(20))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(20))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.9, generatePath(20))
        knapsackExecuter.writeResultDataFile()

        //250 kg, wmax=10, vmax=130
        knapsackExecuter = new KnapsackExecuter(250, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(50))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.01, generatePath(50))
        knapsackExecuter.writeResultDataFile()
    }

    private void executeRestOfTestCases() {
        //750kg, wmax=150, vmax=250
        knapsackExecuter = new KnapsackExecuter(750, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(100))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.5, generatePath(100))
        knapsackExecuter.writeResultDataFile()

        //1500 kg, wmax=250, vmax=125
        knapsackExecuter = new KnapsackExecuter(1500, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(200))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.7, generatePath(200))
        knapsackExecuter.writeResultDataFile()

        //3000kg, wmax=150, vmax=150
        knapsackExecuter = new KnapsackExecuter(10000, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(1000))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.08, generatePath(1000))
        knapsackExecuter.writeResultDataFile()

        //15tones, wmax=250, vmax=175
        knapsackExecuter = new KnapsackExecuter(15000, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.GreedySearch, 0.0, generatePath(50000))
        knapsackExecuter.writeResultDataFile()

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.8, generatePath(50000))
        knapsackExecuter.writeResultDataFile()

    }

    void randomTests() {
        knapsackExecuter = new KnapsackExecuter(15000, 0.0)

        knapsackExecuter.processData(KnapsackSolutionType.RandomSearch, 0.8, generatePath(50000))
        knapsackExecuter.writeResultDataFile()
    }

    void bugTest() {
        knapsackExecuter = new KnapsackExecuter(50, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.ExtensiveSearch, 0.0, generatePath(15))
        knapsackExecuter.writeResultDataFile()
    }

    void hcTest1() {
        knapsackExecuter = new KnapsackExecuter(10000, 0.0)
        knapsackExecuter.processData(KnapsackSolutionType.StochasticHillClimbing, 0.7, generatePath(1000))
        knapsackExecuter.writeResultDataFile()
    }

    public static void main(String[] args) {
        Application application = new Application()
        //application.generateFiles()
        //application.executeSmallNumberTestCases()
        //application.executeRestOfTestCases()
        //application.bugTest()
        application.hcTest1()
    }
}