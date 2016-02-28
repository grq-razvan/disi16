package solution.knapsack.general

import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 27/02/16.
 */
class KnapsackExecuter {
    private static final String FILE_DATA_PATH = "src/main/resources/knapsack/data"
    private static final String FILE_RESULT_PATH = "src/main/resources/knapsack/results/data"

    private inputData
    private processedData

    private final KnapsackDataManager dataManager
    private final KnapsackResultManager resultManager

    public KnapsackExecuter(Integer maxWeight, Double randomCertaintyEpsilon) {
        this.dataManager = new KnapsackDataManager()
        this.resultManager = new KnapsackResultManager(maxWeight, randomCertaintyEpsilon)
    }

    void writeTestDataFile(int itemCount, Integer maxWeight = null, Integer maxValue = null, String path = FILE_DATA_PATH + "-${itemCount}.txt") {
        inputData = dataManager.generateData(itemCount, maxWeight, maxValue)
        dataManager.writeDataFile(inputData, path)
    }

    void writeResultDataFile(String path = FILE_RESULT_PATH + "-${inputData.size()}.txt" + "-${processedData.solution}.txt") {
        resultManager.writeResultFile(processedData.results, path)
    }

    void processData(KnapsackSolutionType type = KnapsackSolutionType.ExtensiveSearch, Integer randomParameter = 0, String inputFilePath = FILE_DATA_PATH + "-${inputData.size()}.txt") {
        inputData = dataManager.readDataFile(inputFilePath)
        processedData =
                [
                        results : resultManager.generateResult(inputData, type, randomParameter),
                        solution: type.solutionType
                ]
    }

}
