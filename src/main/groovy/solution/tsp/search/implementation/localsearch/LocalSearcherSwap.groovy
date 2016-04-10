package solution.tsp.search.implementation.localsearch

import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 06/04/16.
 */
class LocalSearcherSwap extends AbstractTSPSearcher {

    LocalSearcherSwap(Integer cityCount) {
        super.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.Local2Swap
        this.maxNumber = cityCount
        this.runtimeParams.iterations = 650
        this.runtimeParams.restarts = 50
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
            for (j in (0..<params.iterations)) {
                int firstIndex = randomGenerator.nextInt(candidate.cities.indices.first(), candidate.cities.indices.last() - 1)
                int lastIndex = randomGenerator.nextInt(firstIndex + 1, candidate.cities.indices.last())
                Route neighbor = create2SwapRoute(candidate, firstIndex, lastIndex)
                if (neighbor.isBetter(candidate)) {
                    candidate = neighbor
                }
            }
            routes.add(candidate)
        }
        def endTime = System.currentTimeMillis()
        def maxRoute = routes.max { it.totalCost }
        maxRoute.executionTime = endTime - startTime
        return [maxRoute]
    }
}
