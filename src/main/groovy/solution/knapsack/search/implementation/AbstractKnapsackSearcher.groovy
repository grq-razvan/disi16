package solution.knapsack.search.implementation

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 27/02/16.
 */
abstract class AbstractKnapsackSearcher implements ISolver<Knapsack> {

    List<Item> items
    List<Knapsack> knapsacks
    Double randomSearchParameter
    KnapsackSolutionType type

    abstract List<Knapsack> solve()

    abstract void solveInMemory()

    protected List<Item> convertBinaryStringToItems(String binaryString) {
        List<Item> results = []
        binaryString.eachWithIndex { String entry, int index ->
            if (entry == '1') {
                results.add(items.get(index))
            }
        }
        return results
    }

    protected Knapsack createKnapsackFromBinaryString(Integer maxWeight, String binaryString) {
        Knapsack result = new Knapsack(maxWeight: maxWeight, items: [])
        convertBinaryStringToItems(binaryString).each {
            result.addItem(it)
        }
        return result
    }

    protected static String generateBitString(Number number, int length) {
        String binaryRepresentation = Long.toBinaryString(number.longValue())
        while (binaryRepresentation.length() < length) {
            binaryRepresentation = '0' + binaryRepresentation
        }
        return binaryRepresentation
    }
}