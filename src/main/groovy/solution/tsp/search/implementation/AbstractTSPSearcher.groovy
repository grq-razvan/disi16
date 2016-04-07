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

    protected void initRoute(Route route, List<City> cities, Object dimension) {
        (dimension as Integer).times {
            def randomIndex = randomGenerator.nextInt(cities.indices.first(), cities.indices.last())
            City randomCity = cities.get(randomIndex)
            route.cities.add(randomCity)
            cities.remove(randomCity)
        }
    }

}
