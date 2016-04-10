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
        def firstSwap = temp.subList(i, k)
        def secondSwap = temp.subList(j, t)
        def length = Math.abs(k - j)
        List<City> partOne = get2SwapCities(new Route(cities: firstSwap, maxNumber: firstSwap.size()), firstSwap.indices.first(), firstSwap.indices.last())
        List<City> partTwo = get2SwapCities(new Route(cities: secondSwap, maxNumber: secondSwap.size()), secondSwap.indices.first(), secondSwap.indices.last())
        def result = []
        if (i == 0) {
            result += partOne

        } else {
            result += temp.subList(0, i)
            result += partOne
        }

        result += temp.subList(j, k).findAll { !it in result }
        result += partTwo.findAll { !it in result }
        if (temp.subList(k, t) && !temp.subList(k, t).empty) {
            result += temp.subList(k, t).findAll { !it in result }
        }
        if (result.size() != temp.size()) {
            result.addAll(temp.findAll { !it in result })
        }
        List<City> finalPart = get3MoveCities(new Route(cities: result, maxNumber: initial.maxNumber), j, length, shift)

        return new Route(cities: finalPart, maxNumber: initial.maxNumber)
    }

    private static List<City> get2SwapCities(Route route, int i, int j) {
        return create2SwapRoute(route, i, j).cities
    }

    private static List<City> get3MoveCities(Route route, int i, int length, int shift) {
        return create3MoveRoute(route, i, length, shift).cities
    }

}
