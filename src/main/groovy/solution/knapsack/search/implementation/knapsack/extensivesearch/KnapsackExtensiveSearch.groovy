package solution.knapsack.search.implementation.knapsack.extensivesearch

import model.knapsack.Knapsack
import org.apache.commons.math3.util.ArithmeticUtils
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 26/02/16.
 */
class KnapsackExtensiveSearch extends AbstractKnapsackSearcher {

    KnapsackExtensiveSearch(Integer knapsackMaxWeight) {
        knapsacks = [new Knapsack(maxWeight: knapsackMaxWeight, items: [])]
        items = []
        type = KnapsackSolutionType.ExtensiveSearch
    }

    private static List<String> generateBinaryStrings(Integer itemCount) {
        def result = []
        def max = ArithmeticUtils.pow(2L, (itemCount - 1).longValue())
        for (long currentNumber = 1; currentNumber < max; currentNumber++) {
            String binaryRepresentation = generateBitString(currentNumber, itemCount)
            result.add(binaryRepresentation)
        }

        return result
    }

    public void solveInMemory() {
        this.knapsacks = [computeSolution().sort {
            knapsack1, knapsack2 -> return knapsack2.totalValue <=> knapsack1.totalValue
        }.first()]
    }

    public List<Knapsack> solve() {
        return computeSolution()
    }

    private List<Knapsack> computeSolution() {
        List<String> possibleMatches = generateBinaryStrings(items.size())
        List<Knapsack> results = []
        def maxWeight = this.knapsacks[0].maxWeight
        possibleMatches.eachWithIndex { match, index ->
            results.add createKnapsackFromBinaryString(maxWeight, match)
        }

        return [results.findAll { it.valid }.max { it.totalValue }]
    }

}
