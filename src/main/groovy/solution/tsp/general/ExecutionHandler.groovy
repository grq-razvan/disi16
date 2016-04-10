package solution.tsp.general

import model.tsp.City
import model.tsp.Route
import solution.meta.AbstractExecutionHandler

/**
 *  Created by stefangrecu on 07/04/16.
 */
class ExecutionHandler extends AbstractExecutionHandler<City, Route> implements RuntimePathsContainer {

    ExecutionHandler(Map params) {
        this.dataHandler = new DataHandler()
        this.resultHandler = new ResultHandler(params.cityCount as Integer)
    }

    @Override
    void writeTestDataFile(Map data, String path = DATA_BASE_PATH + "/instance-${cityCount}") {
        inputData = dataHandler.generateData(data)
        dataHandler.writeDataFile(inputData, path)
    }

    @Override
    void writeResultDataFile(String path = createResultFile(inputData.size())) {
        resultHandler.writeResultFile(processedData.results as List<Route>, path)
    }

    @Override
    void processData(Map data, String inputFilePath = getInputFile(inputData.size())) {
        inputData = dataHandler.readDataFile(inputFilePath)
        processedData = [
                results : resultHandler.generateResult(inputData, data),
                solution: data.solutionType
        ]
    }

    @Override
    void createResultHandlerWith(Map data) {
        resultHandler = new ResultHandler(data.cityCount as Integer)
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
