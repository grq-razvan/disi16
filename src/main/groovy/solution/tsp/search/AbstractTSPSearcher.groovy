package solution.tsp.search

import model.tsp.City
import model.tsp.Route
import solution.ISolver

/**
 *  Created by stefangrecu on 24/03/16.
 */
abstract class AbstractTSPSearcher implements ISolver<Route> {

    protected List<City> cities
    protected TSPSolutionType solutionType
    protected Integer maxNumber

    protected List<List<Integer>> generatePermutations() {
        List<Integer> inputs = (0..<maxNumber).collect()
        return inputs.permutations().toList()
    }

}
