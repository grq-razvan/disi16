package solution.knapsack.search.implementation.knapsack
/**
 *  Created by stefangrecu on 27/02/16.
 */
enum KnapsackSolutionType {
    ExtensiveSearch("extensive-search"),
    RandomSearch("random-search"),
    GreedySearch("greedy-search"),
    StochasticHillClimbing("stochastic-hill-climbing"),
    SteepestAscent("steepest-ascent"),
    SimpleExperiment("test-hc-parallel-mr")

    String solutionType

    KnapsackSolutionType(String type) {
        this.solutionType = type
    }
}