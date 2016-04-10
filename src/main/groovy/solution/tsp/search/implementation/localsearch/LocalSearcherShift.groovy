package solution.tsp.search.implementation.localsearch

import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 10/04/16.
 */
class LocalSearcherShift extends AbstractTSPSearcher {
    LocalSearcherShift(Integer cityCount) {
        super.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.Local3Move
        this.maxNumber = cityCount
        this.runtimeParams.iterations = (cityCount * 3.5).toInteger()
        this.runtimeParams.restarts = (this.runtimeParams.iterations as Integer).intdiv(8).intValue()
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
                int randomIndex = randomGenerator.nextInt(candidate.cities.indices.first(), candidate.cities.indices.last() - 1)
                Route neighbor = create3MoveRoute(candidate, randomIndex)
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
