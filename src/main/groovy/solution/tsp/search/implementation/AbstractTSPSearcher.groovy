package solution.tsp.search.implementation

import model.tsp.City
import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.ISolver

/**
 *  Created by stefangrecu on 24/03/16.
 */
abstract class AbstractTSPSearcher implements ISolver<Route> {

    List<City> cities = []
    TSPSolutionType solutionType
    Integer maxNumber = 1
    Map<String, Object> runtimeParams = [:]
    protected RandomDataGenerator randomGenerator

    protected List<List<Integer>> generatePermutations() {
        List<Integer> inputs = (0..<maxNumber).collect()
        return inputs.permutations().toList()
    }

    protected synchronized void initRoute(Route route, List<City> cities, Object dimension = this.maxNumber) {
        (dimension as Integer).times {
            def randomIndex = randomGenerator.nextInt(cities.indices.first(), cities.indices.last())
            City randomCity = cities.get(randomIndex)
            route.cities.add(randomCity)
            cities.remove(randomCity)
        }
    }

    protected synchronized static Route create2SwapRoute(Route initial, int i, int j) {
        List<City> swaps = initial.cities.collect()
        def firstIndex = swaps.indices.first()
        def lastIndex = swaps.indices.last()
        if (i == firstIndex && j == lastIndex) {
            Collections.swap(swaps, firstIndex, lastIndex)
            def cities = swaps.reverse()
            return new Route(cities: cities, maxNumber: initial.maxNumber)
        }
        def cities = swaps.subList(firstIndex, i + 1) + swaps.subList(i + 1, j).reverse() + swaps.subList(j, lastIndex + 1)
        return new Route(cities: cities, maxNumber: initial.maxNumber)
    }

    protected synchronized static Route create3MoveRoute(Route initial, int i, int length = 3, int shift = 1) {
        List<City> temp = initial.cities.collect()
        def firstIndex = temp.indices.first()
        def lastIndex = temp.indices.last()
        def cities = temp.subList(firstIndex, i) +
                temp.subList(i + length, i + length + shift) +
                temp.subList(i, i + length) +
                temp.subList(i + length + shift, lastIndex + 1)
        return new Route(cities: cities, maxNumber: initial.maxNumber)
    }

}
