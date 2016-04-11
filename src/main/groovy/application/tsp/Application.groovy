package application.tsp

import static solution.tsp.search.implementation.TSPSolutionType.*

/**
 * ... Created by stefangrecu on 24/03/16.
 */
class Application {
    public static void main(String[] args) {
        ExecutionDataHolder holder = new ExecutionDataHolder()
        holder.executeReqTests(Local2Swap)
        holder.executeReqTests(Local3Move)
        holder.executeMyTests(LocalExperiment)
        holder.executeMyTests(Mix)
        holder.executeReqTests(LocalExperiment)
        holder.executeReqTests(Mix)
    }
}
