package solution.knapsack.search.implementation.knapsack.hillclimbing.stochastic

import model.knapsack.Knapsack
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractKnapsackHillClimbingSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
class KnapsackSimpleRegionStochasticHillClimbingSearcher extends AbstractKnapsackHillClimbingSearcher {

    //simple one = generate K initial guesses (regions), having the same distance function
    //apply HC in parallel to each
    //get result

    @Override
    List<Knapsack> solve() {
        return null
    }

    @Override
    void solveInMemory() {

    }
}
