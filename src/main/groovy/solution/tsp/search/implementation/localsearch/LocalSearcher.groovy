package solution.tsp.search.implementation.localsearch

import model.tsp.City
import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 06/04/16.
 */
class LocalSearcher extends AbstractTSPSearcher {

    LocalSearcher(Integer cityCount) {
        super.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.Local
        this.maxNumber = cityCount
        this.runtimeParams.iterations = (cityCount * 1.5).toInteger()
        this.runtimeParams.restarts = (this.runtimeParams.iterations as Integer).intdiv(10).intValue()
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        List<Route> routes = []
        params.restarts.times {
            Route candidate = new Route(cities: [], maxNumber: this.maxNumber)
            initRoute(candidate, cities.collect())
            params.iterations.times {
                int firstIndex = randomGenerator.nextInt(0, candidate.cities.size())
                int lastIndex = randomGenerator.nextInt(firstIndex, candidate.cities.size())
                Route neighbor = create2SwapRoute(candidate, firstIndex, lastIndex)
                if (neighbor.isBetter(candidate)) {
                    candidate = neighbor
                }
            }
            routes.add(candidate)
        }
        return [routes.max { it.totalCost }]
    }


    private static Route createNeighborRoute(Route initial, int i, int j) {
        List<City> swaps = initial.cities.collect()
        def firstIndex = swaps.indices.first()
        def lastIndex = swaps.indices.last()
        def cities = swaps.subList(firstIndex, i) + swaps.subList(i, j).reverse() + swaps.subList(j, lastIndex)
        return new Route(cities: cities, maxNumber: initial.maxNumber)
    }
}
