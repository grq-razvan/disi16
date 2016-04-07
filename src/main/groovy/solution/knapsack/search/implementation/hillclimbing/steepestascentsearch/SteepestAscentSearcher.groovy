package solution.knapsack.search.implementation.hillclimbing.steepestascentsearch

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataGenerator
import solution.knapsack.search.implementation.hillclimbing.AbstractHillClimbingSearcher

import static solution.knapsack.search.implementation.KnapsackSolutionType.SteepestAscent

/**
 *  Created by stefangrecu on 12/03/16.
 */
class SteepestAscentSearcher extends AbstractHillClimbingSearcher {

    SteepestAscentSearcher(Integer maxKnapsackWeight, Double randomParameter) {
        this.randomGenerator = new RandomDataGenerator()
        this.adaptiveRandomQuality = randomParameter
        this.maxKnapsackWeight = maxKnapsackWeight
        this.type = SteepestAscent
        this.runtimeParams = [:]
    }

    @Override
    List<Knapsack> solve() {
        this.runtimeParams = adjustRuntimeParameters()
        return computeSolution(runtimeParams)
    }

    private List<Knapsack> computeSolution(Map<String, Integer> params) {
        List<Knapsack> results = []
        params.restarts.times {
            String currentHilltop = createHilltop()
            Knapsack currentKnapsack = createKnapsack(currentHilltop)
            params.iterations.times {
                List<String> neighbors = getNeighbors(currentHilltop)
                Map<String, Object> bestNeighbor = steepestNeighbor(neighbors)
                if (isNeighborBetter(currentKnapsack, bestNeighbor.knapsack as Knapsack)) {
                    currentKnapsack = bestNeighbor.knapsack as Knapsack
                    currentHilltop = bestNeighbor.hilltop as String
                }
            }
            if (currentKnapsack.validate()) {
                results.add(currentKnapsack)
            }
        }
        if (results.empty) {
            return []
        }
        results = results?.sort { k1, k2 -> k2.totalValue <=> k1.totalValue }
        results.empty ? [] : ([results.head()] + results?.tail()?.take(5))
    }
}