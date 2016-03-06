package solution.knapsack.search.implementation.knapsack.hillclimbing.stochastic

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomData
import org.apache.commons.math3.random.RandomDataImpl
import org.apache.commons.math3.util.ArithmeticUtils
import org.apache.commons.math3.util.FastMath
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractKnapsackHillClimbingSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
class KnapsackStochasticHillClimbingSearcher extends AbstractKnapsackHillClimbingSearcher {

    private final RandomData randomGenerator

    KnapsackStochasticHillClimbingSearcher(Integer maxKnapsackWeight, Double epsilon) {
        randomGenerator = new RandomDataImpl()
        type = KnapsackSolutionType.StochasticHillClimbing
        knapsacks = [new Knapsack(maxWeight: maxKnapsackWeight, items: [])]
        randomSearchParameter = epsilon
        items = []
    }

    private String generateRandomCandidateSolution(int length) {
        StringBuilder builder = new StringBuilder()
        while (builder.length() < length) {
            Double x = Math.floor(randomGenerator.nextUniform(0.0, 1.1))

            builder.append(x.intValue())
        }
        return builder.toString()
    }

    private String generateRandomCandidateNeighbour(String candidateSolution) {
        List<Boolean> booleanRepresentation = convertFromBitString(candidateSolution)
        Integer randomIndexToFlip = randomGenerator.nextInt(0, booleanRepresentation.size() - 1)
        booleanRepresentation.set(randomIndexToFlip, !booleanRepresentation[randomIndexToFlip])
        return convertFromBooleanRepresentation(booleanRepresentation)
    }

    @Override
    List<Knapsack> solve() {
        List<Knapsack> localOptima = []
        def params = adjustRuntimeParameters(this.randomSearchParameter)
        def maxWeight = this.knapsacks[0].maxWeight

        params.restarts.times {
            String initialSolution = generateRandomCandidateSolution(items.size())
            Knapsack initialKnapsack = createKnapsackFromBinaryString(maxWeight, initialSolution)
            while (!initialKnapsack.valid && it == 0) {
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

    private def adjustRuntimeParameters(Double epsilon) {
        Integer itemCount = items.size()
        def temp = FastMath.log10(FastMath.sqrt(itemCount.doubleValue()) * epsilon)
        def iterations = (FastMath.cbrt((ArithmeticUtils.pow(itemCount, 2) - itemCount + 41).doubleValue())
                - FastMath.pow(temp, 2.0)) / (1 - epsilon)

        def restarts = iterations.toInteger().intdiv(4)
        [
                iterations: iterations.toInteger(),
                restarts  : restarts
        ]
    }


}
