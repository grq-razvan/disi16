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
        List<City> bestCityRoute = this.cities.permutations().max { cities ->
            Route route = new Route(cities: cities, maxNumber: this.maxNumber)
            route.totalCost
        }
        def endTime = System.currentTimeMillis()
        Route route = new Route(cities: bestCityRoute, maxNumber: this.maxNumber)
        params.time = endTime - startTime
        route.executionTime = params.time
        return [route]
    }
}
