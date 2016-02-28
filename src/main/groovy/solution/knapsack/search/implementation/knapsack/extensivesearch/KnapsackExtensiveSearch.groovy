package solution.knapsack.search.implementation.knapsack.extensivesearch

import model.knapsack.Item
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
        for (long l = 1; l < max; l++) {
            String binaryRepresentation = Long.toBinaryString(l)
            while (binaryRepresentation.length() < itemCount) {
                binaryRepresentation = '0' + binaryRepresentation
            }
            result.add(binaryRepresentation)
        }

        return result
    }

    public void solveInMemory() {

        ArrayList<Knapsack> knapsacks = computeSolution()
        this.knapsacks = [knapsacks.sort {
            knapsack1, knapsack2 -> return knapsack2.totalValue <=> knapsack1.totalValue
        }.first()]

    }

    public List<Knapsack> solve() {
        def result = computeSolution()
        return result
    }

    private List<Knapsack> computeSolution() {
        List<String> possibleMatches = generateBinaryStrings(items.size())
        List<Knapsack> knapsacks = new ArrayList<>()
        possibleMatches.each {
            knapsacks.add(new Knapsack(maxWeight: this.knapsacks[0].maxWeight, items: []))
        }

        possibleMatches.eachWithIndex { match, index ->
            Knapsack currentKnapsack = knapsacks.get(index)
            match.eachWithIndex { String entry, int i ->
                if (entry == '1') {
                    Item currentItem = items.get(i)
                    currentKnapsack.addItem(currentItem)
                }
            }
        }

        return [knapsacks.sort {
            knapsack1, knapsack2 -> knapsack2.totalValue <=> knapsack1.totalValue
        }.findAll {
            it.currentWeight <= it.maxWeight
        }.first()]
    }

}
