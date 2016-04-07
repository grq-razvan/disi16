package solution.tsp.search.implementation

import model.tsp.City
import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator

/**
 *  Created by stefangrecu on 06/04/16.
 */
class LocalSearcher extends AbstractTSPSearcher {

    LocalSearcher(Integer cityCount) {
        super.randomGenerator = new RandomDataGenerator()
        super.solutionType = TSPSolutionType.Local
        super.maxNumber = cityCount
        super.runtimeParams.iterations = (cityCount * 1.5).toInteger()
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        Route candidate = new Route(cities: [], maxNumber: super.maxNumber)
        initRoute(candidate, cities.collect(), super.maxNumber)
        params.iterations.times {
            int firstIndex = randomGenerator.nextInt(0, candidate.cities.size())
            int lastIndex = randomGenerator.nextInt(firstIndex, candidate.cities.size())
            Route neighbor = createNeighborRoute(candidate, firstIndex, lastIndex)
            if (neighbor.isBetter(candidate)) {
                candidate = neighbor
            }
        }
        return [candidate]
    }


    private static Route createNeighborRoute(Route initial, int i, int j) {
        List<City> swaps = initial.cities.collect()
        def firstIndex = swaps.indices.first()
        def lastIndex = swaps.indices.last()
        def cities = swaps.subList(firstIndex, i) + swaps.subList(i, j).reverse() + swaps.subList(j, lastIndex)
        return new Route(cities: cities, maxNumber: initial.maxNumber)
    }
}
