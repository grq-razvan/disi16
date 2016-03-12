package solution.knapsack.search.implementation.knapsack.hillclimbing.experimentsearch

import groovyx.gpars.GParsPool
import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataGenerator
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractHillClimbingSearcher

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 12/03/16.
 */
class ExperimentalSearcher extends AbstractHillClimbingSearcher {

    /*
    * My approach to a multiple restart, multiple region,
    * k-flip neighborhood, parallel algorithm
    * to solve the knapsack problem using steepest ascent hillclimbing.
    *
    * The procedure should be as follows:
    *  - adaptively create a number of restarts/iterations per run
    *  - for range 0 to restarts do
    *   - create R candidate solutions (regions)
    *   - for each region do in parallel
    *       generate neighbors of degree I
    *       apply steepest ascent
    */

    ExperimentalSearcher(Integer maxKnapsackWeight, Double epsilon) {
        this.type = KnapsackSolutionType.Experiment
        this.randomGenerator = new RandomDataGenerator()
        this.adaptiveRandomQuality = epsilon
        this.maxKnapsackWeight = maxKnapsackWeight
        this.runtimeParams = [:]
    }

    @Override
    List<Knapsack> solve() {
        runtimeParams = adjustRuntimeParameters()
        return computeSolution(runtimeParams)
    }

    private List<Knapsack> computeSolution(Map<String, Integer> params) {
        List<Knapsack> localOptima = new CopyOnWriteArrayList<>()
        List<Knapsack> regionOptima = new CopyOnWriteArrayList<>()
        List<Knapsack> results = new CopyOnWriteArrayList<>()
        params.restarts.times {
            List<String> candidateHilltops = createHilltopRegions(params.regions)
            GParsPool.withPool {
                candidateHilltops.eachParallel { String currentHilltop ->
                    Knapsack currentKnapsack = createKnapsack(currentHilltop)
                    params.iterations.times { Integer iterationIndex ->
                        IntRange degreeRange = (0..params.maxDegree)
                        List<String> tempNeighbors = getNeighbors(currentHilltop,
                                degreeRange.get(iterationIndex) ?: degreeRange.first())
                        List<String> neighbors = new CopyOnWriteArrayList<>(tempNeighbors)
                        def steepestNeighbor = new ConcurrentHashMap<>(steepestNeighbor(neighbors))
                        if (isNeighborBetter(currentKnapsack, steepestNeighbor.knapsack as Knapsack)) {
                            currentHilltop = steepestNeighbor.hilltop as String
                            currentKnapsack = steepestNeighbor.knapsack as Knapsack
                        }
                    }
                    if (currentKnapsack.validate()) {
                        localOptima.add(currentKnapsack)
                    }
                }
                if (!localOptima.empty) {
                    regionOptima.add(localOptima?.max())
                }
            }
        }
        if (!regionOptima.empty) {
            results += regionOptima
        }
        if (results.empty) {
            return []
        } else {
            results = results.sort { k1, k2 -> k2.totalValue <=> k1.totalValue }
            return [results.head()] + results.tail().take(5)
        }
    }
}
