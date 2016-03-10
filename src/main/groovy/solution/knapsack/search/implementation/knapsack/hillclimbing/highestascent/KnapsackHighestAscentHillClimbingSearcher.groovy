package solution.knapsack.search.implementation.knapsack.hillclimbing.highestascent

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataImpl
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractKnapsackHillClimbingSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
class KnapsackHighestAscentHillClimbingSearcher extends AbstractKnapsackHillClimbingSearcher {

    KnapsackHighestAscentHillClimbingSearcher(Integer maxWeight, Double epsilon) {
        this.knapsacks = [new Knapsack(maxWeight: maxWeight, items: [])]
        this.randomSearchParameter = epsilon
        this.randomGenerator = new RandomDataImpl()
        this.items = []
        this.type = KnapsackSolutionType.SteepestAscent
    }


    private List<Knapsack> computeSolution() {
        String solution = generateRandomCandidateSolution(items.size())
        Knapsack solutionKnapsack = createKnapsackFromBinaryString(this.knapsacks[0].maxWeight, solution)
        List<Knapsack> solutions = []
        def controlParams = adjustRuntimeParameters(this.randomSearchParameter, items.size())
        controlParams.iterations.times {
            solutions += [generateValidKnapsacksFromNeighbours(this.knapsacks[0].maxWeight,
                    generateFlipNeighbours(solution)).collect { Knapsack knapsack -> knapsack.isBetterThan(solutionKnapsack) }.first()]
        }
        return [solutions.max { it.totalValue }]
    }

    private List<Knapsack> boringComputeSolution() {
        String solution = generateRandomCandidateSolution(items.size())
        List<String> neighborSolutions = generateFlipNeighbours(solution)
        Knapsack solutionKnapsack = createKnapsackFromBinaryString(this.knapsacks[0].maxWeight, solution)
        def solutions = []
        def controlParams = adjustRuntimeParameters(randomSearchParameter, items.size())
        controlParams.iterations.times {
            List<Knapsack> neighbors = generateValidKnapsacksFromNeighbours(this.knapsacks[0].maxWeight,
                    neighborSolutions)
            for (neighbor in neighbors) {
                if (neighbor.isBetterThan(solutionKnapsack)) {
                    solutionKnapsack = neighbor
                }
            }
            solutions.add(solutionKnapsack)
        }
        return solutions
    }

    @Override
    List<Knapsack> solve() {
        return boringComputeSolution()
    }

    @Override
    void solveInMemory() {
        knapsacks = computeSolution()
    }
}
