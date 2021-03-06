package solution.tsp.search.implementation.localsearch

import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 10/04/16.
 */
class LocalSearcherExperiment extends AbstractTSPSearcher {

    LocalSearcherExperiment(Integer cityCount) {
        super.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.LocalExperiment
        this.maxNumber = cityCount
        this.runtimeParams.iterations = 75000
        this.runtimeParams.restarts = 20
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        List<Route> routes = []
        if (params.restarts == 0) {
            params.restarts = 1
        }
        def startTime = System.currentTimeMillis()
        for (i in (0..<params.restarts)) {
            Route candidate = new Route(cities: [], maxNumber: this.maxNumber)
            initRoute(candidate, cities.collect())
            def entries = []
            for (j in (0..<params.iterations)) {
                def cityIndices = candidate.cities.indices
                int firstIndex = randomGenerator.nextInt(cityIndices.first(), cityIndices.last().intdiv(2).intValue() - 1)
                int secondIndex = randomGenerator.nextInt(firstIndex + 1, cityIndices.last().intdiv(2).intValue())
                int thirdIndex = randomGenerator.nextInt(cityIndices.last().intdiv(2).intValue() + 1, cityIndices.last() - 1)
                int lastIndex = randomGenerator.nextInt(thirdIndex + 1, cityIndices.last())
                Route neighbor = createQuadSwapMoveRoute(candidate, firstIndex, secondIndex, thirdIndex, lastIndex)
                if (neighbor.isBetter(candidate)) {
                    entries.add(neighbor)
                    candidate = neighbor
                }
            }
            if (!entries.empty) {
                routes += entries.max { it.totalCost }
            }

        }
        def endTime = System.currentTimeMillis()
        def maxRoute = routes.max { it.totalCost }
        maxRoute.executionTime = endTime - startTime
        return [maxRoute]
    }
}
