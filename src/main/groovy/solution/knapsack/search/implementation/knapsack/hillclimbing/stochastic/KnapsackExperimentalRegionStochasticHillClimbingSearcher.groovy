package solution.knapsack.search.implementation.knapsack.hillclimbing.stochastic

import model.knapsack.Knapsack
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractKnapsackHillClimbingSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
class KnapsackExperimentalRegionStochasticHillClimbingSearcher extends AbstractKnapsackHillClimbingSearcher {

    //exprimental one: (generate K initial guesses, with different metrics) -> regions
    //for each region, apply stochastic paralleled hc algorithm, with proper metric


    @Override
    List<Knapsack> solve() {
        return null
    }

    @Override
    void solveInMemory() {

    }
}
