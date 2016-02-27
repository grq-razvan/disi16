package solution.knapsack.general

import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 * Created by stefangrecu on 27/02/16.
 */
class KnapsackExecuter {
    private static final String FILE_DATA_PATH = "src/main/resources/knapsack/data"
    private static final String FILE_RESULT_PATH = "src/main/resources/knapsack/results/data"

    private inputData
    private processedData

    private final KnapsackDataManager dataManager
    private final KnapsackResultManager resultManager

    public KnapsackExecuter() {
        this.dataManager = new KnapsackDataManager()
        this.resultManager = new KnapsackResultManager()
    }

    void writeTestDataFile(int itemCount, Integer maxWeight = null, Integer maxValue = null, String path = FILE_DATA_PATH + "${itemCount}.txt") {
        inputData = dataManager.generateData(itemCount, maxWeight, maxValue)
        dataManager.writeDataFile(inputData, path)
    }

    void writeResultDataFile(String path = FILE_RESULT_PATH + "${processedData.solution}.txt") {
        resultManager.writeResultFile(processedData.results, path)
    }

    void processData(String inputFilePath = FILE_DATA_PATH + "${inputData.size()}.txt", KnapsackSolutionType type = KnapsackSolutionType.ExtensiveSearch, Integer randomParameter = 0) {
        def data = dataManager.readDataFile(inputFilePath)
        processedData =
                [
                        results : resultManager.generateResult(data, type, randomParameter),
                        solution: type.solutionType
                ]
    }
}
