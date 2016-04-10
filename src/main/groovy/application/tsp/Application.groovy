package application.tsp

import solution.tsp.search.implementation.TSPSolutionType

/**
 * ... Created by stefangrecu on 24/03/16.
 */
class Application {
    public static void main(String[] args) {
        ExecutionDataHolder holder = new ExecutionDataHolder()
        holder.executeMyTests(TSPSolutionType.SimulatedAnnealing)
    }
}
