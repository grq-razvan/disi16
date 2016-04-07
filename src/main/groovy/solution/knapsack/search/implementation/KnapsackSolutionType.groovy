package solution.knapsack.search.implementation
/**
 *  Created by stefangrecu on 27/02/16.
 */
enum KnapsackSolutionType {
    Exhaustive("exhaustive-search"),
    Stochastic("random-search"),
    Greedy("greedy-search"),
    RandomHillClimbing("stochastic-hill-climbing"),
    SteepestAscent("steepest-ascent"),
    NextAscent("next-ascent"),
    Experiment("experiment")

    String solutionType

    KnapsackSolutionType(String type) {
        this.solutionType = type
    }
}