package solution.tsp.general

import model.tsp.City
import model.tsp.Route
import solution.meta.AbstractExecutionHandler
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 07/04/16.
 */
class ExecutionHandler extends AbstractExecutionHandler<City, Route> implements RuntimePathsContainer {

    ExecutionHandler(Integer cityCount) {
        this.dataHandler = new DataHandler()
        this.resultHandler = new ResultHandler(cityCount)
    }

    void writeTestDataFile(int cityCount, Number xMax = null, Number yMax = null, String path = DATA_BASE_PATH + "/instance-${cityCount}") {
        inputData = dataHandler.generateData(cityCount, xMax, yMax)
        dataHandler.writeDataFile(inputData, path)
    }

    void writeResultDataFile(String path = createResultFile(inputData.size())) {
        resultHandler.writeResultFile(processedData.results as List<Route>, path)
    }

    void processData(TSPSolutionType solutionType = TSPSolutionType.Exhaustive, String inputFilePath = getInputFile(inputData.size())) {
        inputData = dataHandler.readDataFile(inputFilePath)
        processedData = [
                results : resultHandler.generateResult(inputData, solutionType),
                solution: solutionType
        ]
    }

    void createResultHandlerWith(int cityCount) {
        resultHandler = new ResultHandler(cityCount)
    }

    private static String getInputFile(Object cityCount) {
        def builder = new StringBuilder()
        def instance = INSTANCES_METADATA.find {
            it.dimension == cityCount
        }
        if (instance) {
            builder.append(DATA_BASE_PATH).append(instance.filePath)
        } else {
            throw new NullPointerException("There is no instance with that name.")
        }
        return builder.toString()
    }

    private String createResultFile(Object cityCount) {
        def builder = new StringBuilder()
        def instance = INSTANCES_METADATA.find {
            it.dimension == cityCount
        }
        if (!instance) {
            throw new NullPointerException("There is no instance with that name.")
        } else {
            builder.append(RESULT_BASE_PATH).append(processedData.solution).append('-').append(instance.filePath)
        }
        return builder.toString()
    }
}
