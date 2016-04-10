package solution.tsp.search.implementation.localgreedysearch

import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 06/04/16.
 */
class LocalGreedySearcher extends AbstractTSPSearcher {

    LocalGreedySearcher(Integer maxNumber) {
        super.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.Mix
        this.maxNumber = maxNumber
        this.runtimeParams.iterations = (maxNumber * 3.5).toInteger()
        this.runtimeParams.restarts = (this.runtimeParams.iterations as Integer).intdiv(8).intValue()
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        CopyOnWriteArrayList<Route> routes = []
        if (params.restarts == 0) {
            params.restarts = 2
        }
        def start = System.currentTimeMillis()
        params.restarts.times {
            def tempCities = cities.collect()
            Route route = new Route(cities: [], maxNumber: this.maxNumber)
            initRoute(route, tempCities, this.maxNumber)
            for (int count in (0..<params.iterations)) {
                int startIndex = route.cities.indices.first()
                int lastIndex = route.cities.indices.last()
                List<Route> moves = []
                for (int i = startIndex; i < lastIndex - 1; i++) {
                    List<Route> swaps = []
                    for (int j = i + 1; j < lastIndex; j++) {
                        def otherRoute = create2SwapRoute(route, i, j)
                        if (otherRoute.isBetter(route)) {
                            swaps.add(otherRoute)
                            route = otherRoute
                        }
                    }
                    def otherRoute = create3MoveRoute(route, i, 3)
                    if (otherRoute.isBetter(swaps.empty ? route : swaps.max { it.totalCost })) {
                        moves.add(otherRoute)
                        route = otherRoute
                    }
                    routes += moves
                    routes += swaps
                }

            }
        }
        def finish = System.currentTimeMillis()
        Route result = routes.max { it.totalCost }
        result.executionTime = finish - start
        return [result]
    }
}
