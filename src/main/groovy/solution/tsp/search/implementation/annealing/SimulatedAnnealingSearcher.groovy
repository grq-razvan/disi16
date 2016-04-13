package solution.tsp.search.implementation.annealing

import model.tsp.City
import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

/**
 *  Created by stefangrecu on 10/04/16.
 */
class SimulatedAnnealingSearcher extends AbstractTSPSearcher implements SimulatedAnnealing {

    SimulatedAnnealingSearcher(Integer maxNumber) {
        super.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.SimulatedAnnealing
        this.cities = []
        this.maxNumber = maxNumber
        this.runtimeParams.restarts = 1000
        temperature = 10000.0
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        List<Route> routes = []
        if (params.restarts == 0) {
            params.restarts = 1
        }
        def startTime = System.currentTimeMillis()
        params.restarts.times { Integer iteration ->
            Route candidate = new Route(cities: [], maxNumber: this.maxNumber)
            List<City> shuffledCities = cities.collect()
            initRoute(candidate, shuffledCities)
            def entries = []
            while (temperature > MIN_TEMPERATURE) {
                int firstIndex = randomGenerator.nextInt(candidate.cities.indices.first(), candidate.cities.indices.last() - 1)
                int lastIndex = randomGenerator.nextInt(firstIndex + 1, candidate.cities.indices.last())
                Route neighbor = create2SwapRoute(candidate, firstIndex, lastIndex)
                if (neighbor.isBetter(candidate)) {
                    entries += neighbor
                    candidate = neighbor
                } else {
                    Double acceptanceProbability = computeAcceptanceProbability(candidate.totalCostMinimum.doubleValue(),
                            neighbor.totalCostMinimum.doubleValue())
                    if (acceptanceProbability > randomGenerator.nextUniform(0.0, 1.0)) {
                        entries += neighbor
                        candidate = neighbor
                    }
                }
                coolTemperatureLinear()
            }
            heatUp(applyFraction(10000.0, iteration))
            if (!entries.empty) {
                routes += entries.max { it.totalCost }
            }
        }
        def endTime = System.currentTimeMillis()
        def maxRoute = routes.max { it.totalCost }
        maxRoute.executionTime = endTime - startTime
        return [maxRoute]
    }

    @Override
    Double computeAcceptanceProbability(Double solutionEnergy, Double neighborEnergy) {
        return Math.exp((Math.abs(neighborEnergy - solutionEnergy)) / temperature)
    }
}
