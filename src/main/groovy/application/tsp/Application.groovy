package application.tsp

import static solution.tsp.search.implementation.TSPSolutionType.Mix
import static solution.tsp.search.implementation.TSPSolutionType.SimulatedAnnealing

/**
 * ... Created by stefangrecu on 24/03/16.
 */
class Application {
    public static void main(String[] args) {
        ExecutionDataHolder holder = new ExecutionDataHolder()
        holder.executeTests(Mix)
        holder.executeTests(SimulatedAnnealing)
    }
}
