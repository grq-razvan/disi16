package solution.knapsack.search.implementation

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 27/02/16.
 */
abstract class AbstractKnapsackSearcher implements ISolver<Knapsack> {

    protected List<Item> items = []
    protected Integer maxKnapsackWeight
    protected Double adaptiveRandomQuality
    protected KnapsackSolutionType type

    abstract List<Knapsack> solve()

    protected synchronized List<Item> convertBinaryStringToItems(String binaryString) {
        List<Item> results = new CopyOnWriteArrayList<>()
        binaryString.eachWithIndex { String entry, int index ->
            if (entry == '1') {
                results.add(items.get(index))
            }
        }
        return results.toList()
    }

    protected synchronized Knapsack createKnapsackFromBinaryString(Integer maxWeight, String binaryString) {
        Knapsack result = new Knapsack(maxWeight: maxWeight)
        convertBinaryStringToItems(binaryString).each {
            result.addItem(it)
        }
        return result
    }

    protected synchronized static String generateBitString(Number number, int length) {
        StringBuffer binaryRepresentation = new StringBuffer(Long.toBinaryString(number.longValue()))
        while (binaryRepresentation.length() < length) {
            binaryRepresentation.insert(0, '0')
        }
        return binaryRepresentation.toString()
    }
}