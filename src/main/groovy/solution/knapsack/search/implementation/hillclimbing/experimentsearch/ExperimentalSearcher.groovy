package solution.knapsack.search.implementation.hillclimbing.experimentsearch

import groovyx.gpars.GParsPool
import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataGenerator
import solution.knapsack.search.implementation.KnapsackSolutionType
import solution.knapsack.search.implementation.hillclimbing.AbstractHillClimbingSearcher

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 12/03/16.
 */
class ExperimentalSearcher extends AbstractHillClimbingSearcher {

    /*
    * My approach to a multiple restart, multiple region,
    * k-flip neighborhood, parallel algorithm
    * to solve the knapsack problem using steepest ascent hill climbing.
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
        runtimeParams << adjustRuntimeParameters()
        return computeSolution(runtimeParams)
    }

    private List<Knapsack> computeSolution(Map<String, Integer> params) {
        List<Knapsack> finalResult = new CopyOnWriteArrayList<>()
        List<Knapsack> regionIterationsHilltops = new CopyOnWriteArrayList<>()
        List<Knapsack> regionRestartsHilltops = new CopyOnWriteArrayList<>()
        params.restarts.times {
            List<String> candidateHilltops = createHilltopRegions(params.regions)
            GParsPool.withPool {
                candidateHilltops.eachParallel { String currentHilltop ->
                    Knapsack currentKnapsack = createKnapsack(currentHilltop)
                    params.iterations.times { Integer iterationIndex ->
                        List<String> createdNeighbors = getNeighbors(currentHilltop,
                                getDegree(params.maxDegree, iterationIndex))
                        List<String> neighbors = new CopyOnWriteArrayList<>(createdNeighbors)
                        def steepestNeighbor = new ConcurrentHashMap<>(steepestNeighbor(neighbors))
                        if (isNeighborBetter(currentKnapsack, steepestNeighbor.knapsack as Knapsack)) {
                            currentHilltop = steepestNeighbor.hilltop as String
                            currentKnapsack = steepestNeighbor.knapsack as Knapsack
                        }
                    }
                    if (currentKnapsack.validate()) {
                        regionIterationsHilltops.add(currentKnapsack)
                    }
                }
                if (!regionIterationsHilltops.empty) {
                    regionRestartsHilltops.add(regionIterationsHilltops?.max { it.totalValue })
                }
            }
        }
        if (!regionRestartsHilltops.empty) {
            finalResult += regionRestartsHilltops
        }
        if (finalResult.empty) {
            return []
        } else {
            finalResult = finalResult.sort { k1, k2 -> k2.totalValue <=> k1.totalValue }
            return [finalResult.head()] + finalResult.tail().take(5)
        }
    }

    private synchronized int getDegree(Integer maxDegree, Integer iterationIndex) {
        if (maxDegree == 1) {
            return maxDegree
        } else {
            IntRange range = (1..maxDegree)
            if (iterationIndex >= range.size()) {
                return range.first()
            } else {
                return range.get(iterationIndex)
            }
        }
    }
}
