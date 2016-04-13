package application.tsp

import static solution.tsp.search.implementation.TSPSolutionType.Local3Move

/**
 * ... Created by stefangrecu on 24/03/16.
 */
class Application {
    public static void main(String[] args) {
        ExecutionDataHolder holder = new ExecutionDataHolder()
        holder.executeTests(Local3Move)
    }
}
