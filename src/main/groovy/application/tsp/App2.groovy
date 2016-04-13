package application.tsp

import static solution.tsp.search.implementation.TSPSolutionType.*

/**
 *  stefa on 12:04:2016.
 */
class App2 {
    public static void main(String[] args) {
        ExecutionDataHolder holder = new ExecutionDataHolder()
        holder.executeReqTests(LocalExperiment)
        holder.executeReqTests(Mix)
        holder.executeReqTests(SimulatedAnnealing)
    }
}
