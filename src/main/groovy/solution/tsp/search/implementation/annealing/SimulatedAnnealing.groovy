package solution.tsp.search.implementation.annealing

import org.apache.commons.math3.util.FastMath

/**
 *  Created by stefangrecu on 10/04/16.
 */
trait SimulatedAnnealing {
    Double temperature
    static final Double MIN_TEMPERATURE = 1.0
    private final Double ALPHA = 0.99

    abstract Double computeAcceptanceProbability(Double solutionEnergy, Double neighborEnergy)

    Double coolTemperatureLinear() {
        temperature *= ALPHA
    }

    void heatUp(Double temperature) {
        setTemperature(temperature)
    }

    Double applyLog(Double temperature, int i) {
        return temperature / FastMath.log(2, i + 1)
    }

    Double applyFraction(Double temperature, int i) {
        return temperature / i
    }
}