package solution.tsp.general

import io.converter.implementation.tsp.ResultConverter
import io.writer.implementation.tsp.ResultWriter
import model.tsp.City
import model.tsp.Route
import solution.meta.AbstractResultHandler
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType
import solution.tsp.search.implementation.exhaustivesearch.ExhaustiveSearcher
import solution.tsp.search.implementation.greedysearch.GreedySearcher
import solution.tsp.search.implementation.localgreedysearch.LocalGreedySearcher
import solution.tsp.search.implementation.localsearch.LocalSearcher

/**
 *  Created by stefangrecu on 07/04/16.
 */
class ResultHandler extends AbstractResultHandler<City, Route> {

    ResultHandler(Integer cityCount) {
        this.resultWriter = new ResultWriter()
        this.resultConverter = new ResultConverter()
        solvers = []
        initializeSolvers(cityCount)
    }

    private void addSolver(AbstractTSPSearcher searchSolution) {
        solvers.add(searchSolution)
    }

    private void initializeSolvers(Integer cityCount) {
        if (solvers.empty) {
            addSolver new ExhaustiveSearcher(cityCount)
            addSolver new GreedySearcher(cityCount)
            addSolver new LocalGreedySearcher(cityCount)
            addSolver new LocalSearcher(cityCount)
        }
    }

    @Override
    List<Route> generateResult(Collection<City> entities, Map data) {
        AbstractTSPSearcher searcher = getSolver(TSPSolutionType.valueOf(data.solutionTypeProvider))
        searcher.cities = entities
        return searcher.solve()
    }

    @Override
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
