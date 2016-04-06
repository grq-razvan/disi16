package solution.tsp.search.general

import io.converter.IResultConverter
import io.converter.implementation.tsp.ResultConverter
import io.writer.IFileWriter
import io.writer.implementation.tsp.ResultWriter
import model.tsp.City
import model.tsp.Route
import solution.ISolver
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 07/04/16.
 */
class ResultHandler {
    private final IFileWriter<Route> resultWriter
    private final IResultConverter<Route> resultConverter
    private final List<ISolver<Route>> solvers

    ResultHandler(Integer cityCount) {
        resultWriter = new ResultWriter()
        resultConverter = new ResultConverter()
        solvers = []
    }

    private void addSolver(AbstractTSPSearcher searchSolution) {
        solvers.add(searchSolution)
    }

    private void initializeSolvers(Integer cityCount) {

    }

    List<Route> generateResult(Collection<City> data, TSPSolutionType solutionTypeProvider = TSPSolutionType.Exhaustive) {
        AbstractTSPSearcher searcher = getSolver(solutionTypeProvider)
        searcher.cities = data
        return searcher.solve()
    }

    void writeResultFile(List<Route> result, String path) {
        def resultMap = resultConverter.convertResultToMap(result)
        resultWriter.writeLines(path, resultMap)
    }

    private AbstractTSPSearcher getSolver(TSPSolutionType solutionType) {
        for (solver in solvers) {
            if (solver instanceof AbstractTSPSearcher) {
                def possibleResult = solver as AbstractTSPSearcher
                if (possibleResult.solutionType == solutionType) {
                    return possibleResult
                }
            }
        }
        return null
    }
}
