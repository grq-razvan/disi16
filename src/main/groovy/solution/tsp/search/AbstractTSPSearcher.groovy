package solution.tsp.search

import model.tsp.Route

/**
 *  Created by stefangrecu on 24/03/16.
 */
class AbstractTSPSearcher {

    protected Route route

    protected static List<List<Integer>> generatePermutations(Integer maxNumber) {
        def result = []
        List<Integer> inputs = (0..<maxNumber).collect()
        result = inputs.permutations().toList()
        return result;
    }
}
