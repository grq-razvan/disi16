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

    protected void initRoute(Route route, List<City> cities, Object dimension = this.maxNumber) {
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
        if (i + length + shift >= lastIndex) {
            Collections.rotate(temp, i + length + shift)
            i = lastIndex - length
        }

        if (i + length > lastIndex) {
            Collections.rotate(temp, i + length)
            i = lastIndex - length
        }

        if (i == lastIndex) {
            Collections.rotate(temp, 1)
            i = firstIndex
        }
        def cities = temp.subList(firstIndex, i) +
                temp.subList(i + length, i + length + shift) +
                temp.subList(i, i + length) +
                temp.subList(i + length + shift, lastIndex + 1)
        return new Route(cities: cities, maxNumber: initial.maxNumber)
    }

    protected synchronized
    static Route createQuadSwapMoveRoute(Route initial, int i, int j, int k, int t, int shift = 1) {
        List<City> temp = initial.cities.collect()
        if (i + 1 != k - 1) {
            temp = get2SwapCities(new Route(cities: temp, maxNumber: initial.maxNumber), i, k)
        }
        if (j + 1 != t - 1) {
            temp = get2SwapCities(new Route(cities: temp, maxNumber: initial.maxNumber), j, t)
        }
        //def length = Math.abs(k - j)
//        if (j + length + 1 <= temp.indices.last()) {
//            def cityBlock = temp.subList(j, j + length + 1)
//            def cities = temp.removeAll {it in cityBlock}
//            cities = cities.subList(cities.indices.first(), j + length + shift + 1) +
//            cityBlock + cities.subList(j + length + shift + 1, cities.indices.last())
//            return new Route(cities: cities, maxNumber: initial.maxNumber)
//        }
        List<City> cities = get2SwapCities(new Route(cities: temp, maxNumber: initial.maxNumber), j, k)
        return new Route(cities: cities, maxNumber: initial.maxNumber)
    }

    private static List<City> get2SwapCities(Route route, int i, int j) {
        return create2SwapRoute(route, i, j).cities
    }

    private static List<City> get3MoveCities(Route route, int i, int length, int shift) {
        return create3MoveRoute(route, i, length, shift).cities
    }

}
