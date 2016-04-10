package solution.tsp.search.implementation.exhaustivesearch

import model.tsp.City
import model.tsp.Route
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 06/04/16.
 */
class ExhaustiveSearcher extends AbstractTSPSearcher {

    ExhaustiveSearcher(Integer cityCount) {
        this.solutionType = TSPSolutionType.Exhaustive
        this.maxNumber = cityCount
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        def startTime = System.currentTimeMillis()
        List<City> bestCityRoute = this.cities.permutations().sort { a, b ->
            Route aRoute = new Route(cities: a, maxNumber: this.maxNumber)
            Route bRoute = new Route(cities: b, maxNumber: this.maxNumber)
            bRoute.totalCost <=> aRoute.totalCost
        }.head()
        def endTime = System.currentTimeMillis()
        Route route = new Route(cities: bestCityRoute, maxNumber: this.maxNumber)
        params.time = endTime - startTime
        route.executionTime = params.time
        return [route]
    }
}
