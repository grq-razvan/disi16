package solution.knapsack.general

import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 27/02/16.
 */
class ExecutionHandler {
    private static final String FILE_DATA_PATH = "src/main/resources/knapsack/data"
    private static final String FILE_RESULT_PATH = "src/main/resources/knapsack/results/data"

    private inputData
    private processedData

    private final DataHandler dataManager
    private ResultHandler resultManager

    public ExecutionHandler(Integer maxWeight) {
        this.dataManager = new DataHandler()
        this.resultManager = new ResultHandler(maxWeight)
    }

    void writeTestDataFile(int itemCount, Integer maxWeight = null, Integer maxValue = null, String path = FILE_DATA_PATH + "-${itemCount}.txt") {
        inputData = dataManager.generateData(itemCount, maxWeight, maxValue)
        dataManager.writeDataFile(inputData, path)
    }

    void writeResultDataFile(String path = FILE_RESULT_PATH + "-${inputData.size()}.txt" + "-${processedData.solution}.txt") {
        resultManager.writeResultFile(processedData.results, path)
    }

    void processData(KnapsackSolutionType type = KnapsackSolutionType.ExtensiveSearch, Double randomParameter = 0, String inputFilePath = FILE_DATA_PATH + "-${inputData.size()}.txt") {
        inputData = dataManager.readDataFile(inputFilePath)
        processedData =
                [
                        results : resultManager.generateResult(inputData, type, randomParameter),
                        solution: type.solutionType
                ]
    }

    public void setResultManagerMaxWeight(Integer maxWeight) {
        resultManager = new ResultHandler(maxWeight)
    }

}
