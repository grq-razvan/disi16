package solution.tsp.search.implementation

import model.tsp.City
import model.tsp.Route
import org.apache.commons.math3.random.RandomGenerator
import solution.ISolver

/**
 *  Created by stefangrecu on 24/03/16.
 */
abstract class AbstractTSPSearcher implements ISolver<Route> {

    List<City> cities = []
    TSPSolutionType solutionType
    Integer maxNumber = 1
    Map<String, Object> runtimeParams = [:]
    protected RandomGenerator randomGenerator

    protected List<List<Integer>> generatePermutations() {
        List<Integer> inputs = (0..<maxNumber).collect()
        return inputs.permutations().toList()
    }

}
