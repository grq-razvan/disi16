package solution.knapsack.general

import model.knapsack.Knapsack
import solution.knapsack.search.implementation.KnapsackSolutionType

/**
 *  Created by stefangrecu on 27/02/16.
 */
class ExecutionHandler {
    private static final String FILE_DATA_PATH = "src/main/resources/knapsack/data"
    private static final String FILE_RESULT_PATH = "src/main/resources/knapsack/results/data"

    private inputData
    private processedData

    private final DataHandler dataHandler
    private ResultHandler resultHandler

    ExecutionHandler(Integer maxWeight) {
        this.dataHandler = new DataHandler()
        this.resultHandler = new ResultHandler(maxWeight)
    }

    void writeTestDataFile(int itemCount, Integer maxWeight = null, Integer maxValue = null, String path = FILE_DATA_PATH + "-${itemCount}.txt") {
        inputData = dataHandler.generateData(itemCount, maxWeight, maxValue)
        dataHandler.writeDataFile(inputData, path)
    }

    void writeResultDataFile(String path = FILE_RESULT_PATH + "-${inputData.size()}.txt" + "-${processedData.solution}.txt") {
        resultHandler.writeResultFile(processedData.results as List<Knapsack>, path)
    }

    void processData(KnapsackSolutionType type = KnapsackSolutionType.Exhaustive, Double randomParameter = 0, String inputFilePath = FILE_DATA_PATH + "-${inputData.size()}.txt", Integer numberOfRegions = 1, Integer maxFlipDegree = 1) {
        inputData = dataHandler.readDataFile(inputFilePath)
        processedData =
                [
                        results : resultHandler.generateResult(inputData, type, randomParameter, numberOfRegions, maxFlipDegree),
                        solution: type.solutionType
                ]
    }

    public void createResultHandlerWith(Integer maxWeight) {
        resultHandler = new ResultHandler(maxWeight)
    }

}
