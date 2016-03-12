package solution.knapsack.search.implementation.knapsack.extensivesearch

import model.knapsack.Knapsack
import org.apache.commons.math3.util.ArithmeticUtils
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 26/02/16.
 */
class ExhaustiveSearcher extends AbstractKnapsackSearcher {

    ExhaustiveSearcher(Integer maxKnapsackWeight) {
        this.type = KnapsackSolutionType.ExtensiveSearch
        this.maxKnapsackWeight = maxKnapsackWeight
    }

    private static List<String> generateBinaryStrings(Integer itemCount) {
        List<String> result = []
        long numberOfPossibilities = ArithmeticUtils.pow(2L, (itemCount - 1))
        for (long currentNumber = 1; currentNumber < numberOfPossibilities; currentNumber++) {
            String binaryRepresentation = generateBitString(currentNumber, itemCount)
            result.add(binaryRepresentation)
        }
        return result
    }

    public List<Knapsack> solve() {
        return [computeSolution()]
    }

    private Knapsack computeSolution() {
        ((generateBinaryStrings(items.size())
                .collect { String match -> createKnapsackFromBinaryString(maxKnapsackWeight, match) })
                .findAll { Knapsack knapsack -> knapsack.validate() })
                .max()
    }

}
