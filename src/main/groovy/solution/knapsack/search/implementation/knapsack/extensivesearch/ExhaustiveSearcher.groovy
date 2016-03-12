package solution.knapsack.search.implementation.knapsack.extensivesearch

import model.knapsack.Knapsack
import org.apache.commons.math3.util.ArithmeticUtils
import solution.knapsack.search.implementation.AbstractKnapsackSearcher

import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.Exhaustive

/**
 *  Created by stefangrecu on 26/02/16.
 */
class ExhaustiveSearcher extends AbstractKnapsackSearcher {

    ExhaustiveSearcher(Integer maxKnapsackWeight) {
        this.type = Exhaustive
        this.maxKnapsackWeight = maxKnapsackWeight
    }

    public List<Knapsack> solve() {
        return [computeSolution()]
    }

    private Knapsack computeSolution() {
        ((generateBinaryStrings()
                .collect { String match -> createKnapsack(match, maxKnapsackWeight) })
                .findAll { Knapsack knapsack -> knapsack.validate() })
                .max()
    }

    private List<String> generateBinaryStrings(Integer itemCount = items.size()) {
        List<String> result = []
        long numberOfPossibilities = ArithmeticUtils.pow(2L, (itemCount - 1))
        for (long currentNumber = 1; currentNumber < numberOfPossibilities; currentNumber++) {
            String binaryRepresentation = createBitString(currentNumber, itemCount)
            result.add(binaryRepresentation)
        }
        return result
    }

}
