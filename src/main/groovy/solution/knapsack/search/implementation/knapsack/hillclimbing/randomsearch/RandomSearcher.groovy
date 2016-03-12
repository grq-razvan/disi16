package solution.knapsack.search.implementation.knapsack.hillclimbing.randomsearch

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataGenerator
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractHillClimbingSearcher

import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.RandomHillClimbing

/**
 *  Created by stefangrecu on 12/03/16.
 */
class RandomSearcher extends AbstractHillClimbingSearcher {

    RandomSearcher(Integer maxKnapsackWeight, Double randomParameter) {
        this.type = RandomHillClimbing
        this.adaptiveRandomQuality = randomParameter
        this.maxKnapsackWeight = maxKnapsackWeight
        this.randomGenerator = new RandomDataGenerator()
        this.runtimeParams = [:]
    }

    @Override
    List<Knapsack> solve() {
        runtimeParams = adjustRuntimeParameters()
        return computeSolution(runtimeParams)
    }

    private List<Knapsack> computeSolution(Map<String, Integer> params) {
        List<Knapsack> localOptima = []
        params.restarts.times {
            String currentHilltop = createHilltop()
            Knapsack currentKnapsack = createKnapsack(currentHilltop)
            params.iterations.times {
                String neighborHilltop = createHilltopRandomNeighbor(currentHilltop)
                Knapsack neighborKnapsack = createKnapsack(neighborHilltop, maxKnapsackWeight)
                if ((isNeighborBetter(currentKnapsack, neighborKnapsack))) {
                    currentHilltop = neighborHilltop
                    currentKnapsack = neighborKnapsack
                }
            }
            if (currentKnapsack.validate()) {
                localOptima.add(currentKnapsack)
            }
        }
        localOptima = localOptima?.sort { k1, k2 -> k2.totalValue <=> k1.totalValue }
        [localOptima?.head()] + localOptima?.tail()?.take(5)
    }
}
