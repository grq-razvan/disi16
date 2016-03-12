package solution.knapsack.search.implementation.knapsack.hillclimbing.highestascent

import groovyx.gpars.GParsPool
import org.apache.commons.math3.random.RandomDataImpl
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractHillClimbingSearcher

import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 10/03/16.
 */
class HighestAscentMRMVPRSearcher extends AbstractHillClimbingSearcher {

    //go numberOfRestartTimes
    // generate numberOfRegions solutions
    //for each, in parallel generate a unique random T-flip neighbor from range 1..T
    //

    Integer numberOfRegions
    Integer maximumDegreeOfNeighborhood

    HighestAscentMRMVPRSearcher(Integer maxWeight, Double epsilon = 0.0, Integer numberOfRegions = 6, Integer maximumDegreeOfNeighborhood = 4) {
        this.items = []
        this.type = KnapsackSolutionType.SteepestAscentMultipleRegionsMultipleRestartsMultipleNeighbourhoods
        this.knapsacks = [new Knapsack(maxWeight: maxWeight, items: [])]
        this.randomGenerator = new RandomDataImpl()
        this.adaptiveRandomQuality = epsilon
        this.numberOfRegions = numberOfRegions
        this.maximumDegreeOfNeighborhood = maximumDegreeOfNeighborhood
    }

    private List<Knapsack> computeSolution() {
        def controlParams = adjustRuntimeParameters(adaptiveRandomQuality, items.size())
        List<Knapsack> results = new CopyOnWriteArrayList<>()
        List<Knapsack> localOptima = new CopyOnWriteArrayList<>()
        2.times { Integer index ->
            List<Integer> degrees = ((1..maximumDegreeOfNeighborhood).collect {
                it
            })
            Collections.shuffle(degrees)
            List<String> regions = generateRegions(numberOfRegions, items.size())
            GParsPool.withPool {
                regions.eachParallel { String region ->
                    def maxWeight = this.knapsacks[0].maxWeight
                    def regionSolutionHolder = region
                    def regionKnapsackHolder = new Knapsack(maxWeight: maxWeight, items: [])
                    4.times { Integer iteration ->
                        //def outOfBoundsPreventionIndex = iteration < maximumDegreeOfNeighborhood ? iteration : 1
                        def regionKnapsack = createKnapsackFromBinaryString(maxWeight, regionSolutionHolder)
                        def neighbors = getNeighborsOfDegree(regionSolutionHolder, degrees[iteration])
                        for (neighbor in neighbors) {
                            def neighborKnapsack = createKnapsackFromBinaryString(maxWeight, neighbor)
                            if (neighborKnapsack.isBetterThan(regionKnapsack)) {
                                regionKnapsack = neighborKnapsack
                                regionSolutionHolder = neighbor
                            }
                        }
                        regionKnapsackHolder = regionKnapsack
                    }
                    if (!regionKnapsackHolder.items.empty && regionKnapsackHolder.validate())
                        localOptima.add(regionKnapsackHolder)
                }
                results += [localOptima?.findAll { it.validate() }?.max { it?.totalValue }]
            }

        }
        return results.empty ? [] : results.findAll { it.validate() }?.sort { k1, k2 -> k2.totalValue - k1.totalValue }
    }

    @Override
    List<Knapsack> solve() {
        return computeSolution()
    }

    @Override
    void solveInMemory() {

    }
}

//for each region k, get neighbors of degree i
//for (it < itMax)