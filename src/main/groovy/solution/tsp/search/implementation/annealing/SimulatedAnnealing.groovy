package solution.tsp.search.implementation.annealing

import org.apache.commons.math3.util.FastMath

/**
 *  Created by stefangrecu on 10/04/16.
 */
trait SimulatedAnnealing {
    Double temperature
    Double minTemperature
    private final Double ALPHA = 0.995

    abstract Double computeAcceptanceProbability(Double solutionEnergy, Double neighborEnergy)

    Double coolTemperatureLinear() {
        temperature *= ALPHA
    }

    Double coolTemperatureLog(int i) {
        temperature = temperature / FastMath.log(2, i + 1)
    }

    Double coolTemperatureFractional(int i) {
        temperature /= i
    }
}