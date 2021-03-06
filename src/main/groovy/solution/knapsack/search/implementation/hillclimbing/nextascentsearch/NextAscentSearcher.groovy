package solution.knapsack.search.implementation.hillclimbing.nextascentsearch

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataGenerator
import solution.knapsack.search.implementation.hillclimbing.AbstractHillClimbingSearcher

import static solution.knapsack.search.implementation.KnapsackSolutionType.NextAscent

/**
 *  Created by stefangrecu on 12/03/16.
 */
class NextAscentSearcher extends AbstractHillClimbingSearcher {

    NextAscentSearcher(Integer maxKnapsackWeight, Double randomParameter) {
        this.randomGenerator = new RandomDataGenerator()
        this.adaptiveRandomQuality = randomParameter
        this.maxKnapsackWeight = maxKnapsackWeight
        this.type = NextAscent
        this.runtimeParams = [:]
    }

    @Override
    List<Knapsack> solve() {
        runtimeParams = adjustRuntimeParameters()
        return computeSolution(runtimeParams)
    }

    List<Knapsack> computeSolution(Map<String, Integer> params) {
        List<Knapsack> results = []
        String currentHilltop = createHilltop()
        Knapsack currentKnapsack = createKnapsack(currentHilltop)
        params.iterations.times {
            String bestIncrementalHilltop = bestIncrementalHilltop(currentHilltop, 0)
            Knapsack bestIncrementalKnapsack = createKnapsack(bestIncrementalHilltop)
            if (isNeighborBetter(currentKnapsack, bestIncrementalKnapsack)) {
                currentHilltop = bestIncrementalHilltop
                currentKnapsack = bestIncrementalKnapsack
            }
        }
        if (currentKnapsack.validate()) {
            results.add(currentKnapsack)
        }

        if (results.empty) {
            return []
        }
        results = results.sort { k1, k2 -> k2.totalValue <=> k1.totalValue }
        return [results.head()] + results.tail().take(5)
    }

    private String bestIncrementalHilltop(String currentHilltop, int index) {
        boolean repeat = true
        while (repeat) {
            Knapsack currentKnapsack = createKnapsack(currentHilltop)
            List<String> neighbors = getNeighbors(currentHilltop)

            if (index == neighbors.size()) {
                repeat = !repeat
            }

            while (index < neighbors.size()) {
                Knapsack neighborKnapsack = createKnapsack(neighbors[index])
                if (isNeighborBetter(currentKnapsack, neighborKnapsack)) {
                    currentHilltop = neighbors[index]
                    break
                }
                index++
            }
        }

        return currentHilltop
    }
}
