package solution.knapsack.search.implementation.knapsack.hillclimbing.highestascent


import org.apache.commons.math3.random.RandomDataImpl
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractHillClimbingSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
class HighestAscentHillClimbingSearcher extends AbstractHillClimbingSearcher {

    HighestAscentHillClimbingSearcher(Integer maxWeight, Double epsilon) {
        this.knapsacks = [new Knapsack(maxWeight: maxWeight, items: [])]
        this.adaptiveRandomQuality = epsilon
        this.randomGenerator = new RandomDataImpl()
        this.items = []
        this.type = KnapsackSolutionType.SteepestAscent
    }


    private List<Knapsack> computeSolution() {
        String solution = generateRandomCandidateSolution(items.size())
        Knapsack solutionKnapsack = createKnapsackFromBinaryString(this.knapsacks[0].maxWeight, solution)
        List<Knapsack> solutions = []
        def controlParams = adjustRuntimeParameters(this.adaptiveRandomQuality, items.size())
        controlParams.iterations.times {
            solutions += [generateValidKnapsacksFromNeighbours(this.knapsacks[0].maxWeight,
                    generateFlipNeighbours(solution)).collect { Knapsack knapsack -> knapsack.isBetterThan(solutionKnapsack) }.first()]
        }
        return [solutions.max { it.totalValue }]
    }

    private List<Knapsack> boringComputeSolution() {

    }

    @Override
    List<Knapsack> solve() {
        return computeSolution()
    }

    @Override
    void solveInMemory() {
        knapsacks = computeSolution()
    }
}
