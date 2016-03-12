package solution.knapsack.search.implementation.knapsack.hillclimbing.highestascent

import groovyx.gpars.GParsPool
import org.apache.commons.math3.random.RandomDataImpl
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractHillClimbingSearcher

import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 10/03/16.
 */
class HighestAscentMRPRSearcher extends AbstractHillClimbingSearcher {

    HighestAscentMRPRSearcher(Integer maxWeight, Double epsilon) {
        this.items = []
        this.type = KnapsackSolutionType.SteepestAscentMultipleRegionsMultipleRestarts
        this.randomGenerator = new RandomDataImpl()
        this.knapsacks = [new Knapsack(maxWeight: maxWeight, items: [])]
        this.adaptiveRandomQuality = epsilon
    }

    //generate k regions, thread/region; restart i times, retrieve list with maximum/region/run, sorted descending
    //MultipleRestartParallelRegions

    private List<Knapsack> boringComputeSolution() {
        def controlParams = adjustRuntimeParameters(adaptiveRandomQuality, items.size())
        controlParams.regions = controlParams.restarts
        List<Knapsack> localOptima = new CopyOnWriteArrayList<>()
        List<Knapsack> runOptima = new CopyOnWriteArrayList<>()

        controlParams.restarts.times {
            List<String> regions = generateRegions(controlParams.regions.intValue(), items.size())
            GParsPool.withPool {
                regions.eachParallel { String region ->
                    Knapsack solutionKnapsack = createKnapsackFromBinaryString(this.knapsacks[0].maxWeight, region)
                    List<String> neighborSolutions = generateFlipNeighbours(region)
                    controlParams.iterations.times {
                        Knapsack neighborKnapsack = createKnapsackFromBinaryString(this.knapsacks[0].maxWeight, neighborSolutions[it])
                        if (neighborKnapsack.isBetterThan(solutionKnapsack)) {
                            solutionKnapsack = neighborKnapsack
                        }
                    }
                    localOptima.add(solutionKnapsack)
                }
                runOptima += [localOptima.max { it.totalValue }]
            }
        }

        return runOptima.collect { it }.sort { k1, k2 -> k2.totalValue - k1.totalValue }

    }

    private List<Knapsack> computeSolution() {
        def controlParams = adjustRuntimeParameters(adaptiveRandomQuality, items.size())
        controlParams.regions = controlParams.restarts
        List<Knapsack> solutions = []
        List<Knapsack> localOptima = []
        List<Knapsack> runOptima = []

        controlParams.restarts.times {
            List<String> regions = generateRegions(controlParams.regions.intValue(), items.size())
            GParsPool.withPool {
                regions.eachParallel { String region ->
                    Knapsack solutionKnapsack = createKnapsackFromBinaryString(this.knapsacks[0].maxWeight, region)
                    List<String> neighborSolutions = generateFlipNeighbours(region)
                    controlParams.iterations.times {
                        solutions += generateValidKnapsacksFromNeighbours(this.knapsacks[0].maxWeight, neighborSolutions)
                    }
                    localOptima += [solutions.max { Knapsack knapsack -> knapsack.totalValue }]
                }
            }
            runOptima += [localOptima.max { it.totalValue }]
        }
        return runOptima.sort { k1, k2 -> k2.isBetterThan(k1) }
    }

    @Override
    List<Knapsack> solve() {
        return boringComputeSolution()
    }

    @Override
    void solveInMemory() {
        knapsacks = boringComputeSolution()
    }
}
