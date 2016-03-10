package solution.knapsack.search.implementation.knapsack.hillclimbing.stochastic

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataImpl
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractKnapsackHillClimbingSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
class KnapsackStochasticHillClimbingSearcher extends AbstractKnapsackHillClimbingSearcher {

    KnapsackStochasticHillClimbingSearcher(Integer maxKnapsackWeight, Double epsilon) {
        this.randomGenerator = new RandomDataImpl()
        this.type = KnapsackSolutionType.StochasticHillClimbing
        this.knapsacks = [new Knapsack(maxWeight: maxKnapsackWeight, items: [])]
        this.randomSearchParameter = epsilon
        this.items = []
    }


    @Override
    protected synchronized String generateRandomCandidateSolution(int length) {
        StringBuilder builder = new StringBuilder()
        while (builder.length() < length) {
            Double uniformDistributedNumber = Math.floor(randomGenerator.nextUniform(0.0, 1.1))
            builder.append(uniformDistributedNumber.intValue())
        }
        return builder.toString()
    }

    @Override
    List<Knapsack> solve() {
        List<Knapsack> localOptima = []
        def params = adjustRuntimeParameters(this.randomSearchParameter, items.size())
        def maxWeight = this.knapsacks[0].maxWeight

        params.restarts.times { Integer startCount ->
            String initialSolution = generateRandomCandidateSolution(items.size())
            Knapsack initialKnapsack = createKnapsackFromBinaryString(maxWeight, initialSolution)
            while (!initialKnapsack.valid && startCount == 0) {
                initialSolution = generateRandomCandidateSolution(items.size())
                initialKnapsack = createKnapsackFromBinaryString(maxWeight, initialSolution)
            }
            params.iterations.times {
                String candidateNeighbour = generateRandomCandidateNeighbour(initialSolution)
                Knapsack candidateKnapsack = createKnapsackFromBinaryString(maxWeight, candidateNeighbour)
                if (!initialKnapsack.valid || candidateKnapsack.isBetterThan(initialKnapsack)) {
                    initialSolution = candidateNeighbour
                    initialKnapsack = candidateKnapsack
                }
            }
            localOptima.add(initialKnapsack)
        }
        localOptima.sort(true) { k1, k2 -> k2.totalValue <=> k1.totalValue }.take(5)
    }

    @Override
    void solveInMemory() {
        knapsacks = solve()
    }


}
