package solution.knapsack.search.implementation.knapsack.hillclimbing.stochastic

import groovyx.gpars.GParsPool
import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataImpl
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractKnapsackHillClimbingSearcher

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 06/03/16.
 */
class KnapsackSimpleRegionStochasticHillClimbingSearcher extends AbstractKnapsackHillClimbingSearcher {

    //simple one = generate K initial guesses (regions), having the same distance function
    //apply HC in parallel to each
    //get result

    KnapsackSimpleRegionStochasticHillClimbingSearcher(Integer maxKnapsackWeight) {
        this.randomGenerator = new RandomDataImpl()
        this.type = KnapsackSolutionType.SimpleExperiment
        this.knapsacks = [new Knapsack(maxWeight: maxKnapsackWeight, items: [])]
        this.items = []
    }

    private def computeSolution() {
        def results = new CopyOnWriteArrayList<>()
        def localOptima = new CopyOnWriteArrayList()
        def regionOptima = new ConcurrentHashMap()
        def maxWeight = this.knapsacks[0].maxWeight
        def runtimeParams = [
                iterations: 20,
                restarts  : 4
        ]
        GParsPool.withPool {
            for (int restarts = 0; restarts < runtimeParams.restarts; restarts++) {
                List<String> regions = generateRegions(4, items.size())
                for (int regionIndex = 0; regionIndex < regions.size(); regionIndex++) {
                    String initialSolution = regions[regionIndex]
                    Knapsack initialKnapsack = createKnapsackFromBinaryString(maxWeight, initialSolution)
                    while (!initialKnapsack.valid && restarts == 0) {
                        initialSolution = generateRandomCandidateSolution(items.size())
                        initialKnapsack = createKnapsackFromBinaryString(maxWeight, initialSolution)
                    }
                    for (int iterations = 0; iterations < runtimeParams.iterations; iterations++) {
                        String candidateSolution = generateRandomCandidateNeighbour(initialSolution)
                        Knapsack candidateKnapsack = createKnapsackFromBinaryString(maxWeight, candidateSolution)
                        if (!initialKnapsack.valid || candidateKnapsack.isBetterThan(initialKnapsack)) {
                            initialSolution = candidateSolution
                            initialKnapsack = candidateKnapsack
                        }
                    }
                    localOptima.add(initialKnapsack)
                }
                results.add(localOptima.max { it.totalValue })
            }
        }
        results + localOptima
    }

    @Override
    protected synchronized List<String> generateRegions(int regionCount, int size) {
        List<String> regions = []
        regionCount.times {
            regions.add(generateRandomCandidateSolution(size))
        }
        return regions
    }

    @Override
    protected synchronized String generateRandomCandidateSolution(int length) {
        StringBuilder builder = new StringBuilder()
        while (builder.length() < length) {
            Double uniformDistributedNumber = Math.floor(randomGenerator.nextUniform(0.45, 1.1))
            builder.append(uniformDistributedNumber.intValue())
        }
        return builder.toString()
    }

    @Override
    List<Knapsack> solve() {
        computeSolution()
    }

    @Override
    void solveInMemory() {

    }
}

