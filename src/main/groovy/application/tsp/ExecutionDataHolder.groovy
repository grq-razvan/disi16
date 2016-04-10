package application.tsp

import model.tsp.City
import model.tsp.Route
import solution.meta.AbstractExecutionHandler
import solution.tsp.general.ExecutionHandler
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 10/04/16.
 */
class ExecutionDataHolder implements RuntimeConfiguration {

    private AbstractExecutionHandler<City, Route> handler

    ExecutionDataHolder() {
        this.handler = new ExecutionHandler([cityCount: 0])
    }

    void generateFiles() {
        for (Map<String, Integer> params in FILE_PARAMS) {
            handler.writeTestDataFile(params.cityCount, params.xMax, params.yMax, null)
        }
    }

    void executeSingleTest(Map<String, Object> params, solution = null) {
        Integer cityCount = params.dimension as Integer
        if (!solution) {
            List availableTypes = getAvailableTypes(cityCount)
            availableTypes.each { type ->
                handler.createResultHandlerWith([cityCount: cityCount])
                handler.processData([solutionType: type, cityCount: cityCount])
                handler.writeResultDataFile()
            }
        } else {
            handler.createResultHandlerWith([cityCount: cityCount])
            handler.processData([solutionType: solution, cityCount: cityCount])
            handler.writeResultDataFile()
        }

    }

    void executeMyTests(TSPSolutionType solutionType = null) {
        MY_TESTS.each {
            executeSingleTest(it, solutionType)
        }
    }


    void executeReqTests() {
        REQ_TESTS.each {
            executeSingleTest(it)
        }
    }

    void executeTests() {
        executeMyTests()
        executeReqTests()
    }

    private static List<TSPSolutionType> getAvailableTypes(int cityCount) {
        List<TSPSolutionType> availableTypes = []
        if (cityCount <= 20) {
            availableTypes = TSPSolutionType.findAll() - TSPSolutionType.LocalExperiment
        } else {
            availableTypes = TSPSolutionType.findAll() - TSPSolutionType.Exhaustive - TSPSolutionType.LocalExperiment
        }
        return availableTypes
    }

    private static final List<Map<String, Integer>> FILE_PARAMS = [
            [
                    cityCount: 5,
                    xMax     : 175,
                    yMax     : 200],
            [
                    cityCount: 6,
                    xMax     : 75,
                    yMax     : 140],
            [
                    cityCount: 7,
                    xMax     : 150,
                    yMax     : 50],
            [
                    cityCount: 8,
                    xMax     : 510,
                    yMax     : 495],
            [
                    cityCount: 9,
                    xMax     : 316,
                    yMax     : 853],
            [
                    cityCount: 10,
                    xMax     : 760,
                    yMax     : 112],
            [
                    cityCount: 15,
                    xMax     : 847,
                    yMax     : 1302],
            [
                    cityCount: 20,
                    xMax     : 372,
                    yMax     : 873]
    ]
}
