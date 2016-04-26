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
        this.runtimeParams.iterations = 2
        this.runtimeParams.restarts = 1000
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        CopyOnWriteArrayList<Route> routes = []
        if (params.restarts == 0) {
            params.restarts = 1
        }
        def start = System.currentTimeMillis()
        params.restarts.times {
            def tempCities = cities.collect()
            Route route = new Route(cities: [], maxNumber: this.maxNumber)
            initRoute(route, tempCities, this.maxNumber)
            int startIndex = route.cities.indices.first()
            int lastIndex = route.cities.indices.last()
            List<Route> swaps = []
            for (int i = startIndex; i < lastIndex - 1; i++) {
                for (int j = i + 1; j < lastIndex; j++) {
                    def otherRoute = create2SwapRoute(route, i, j)
                    if (otherRoute.isBetter(route)) {
                        swaps.add(otherRoute)
                        route = otherRoute
                    }
                }
                if (!swaps.empty) {
                    routes += swaps.max { it.totalCost }
                }
            }
        }
        def finish = System.currentTimeMillis()
        Route result = routes.max { it.totalCost }
        result.executionTime = finish - start
        return [result]
    }
}
