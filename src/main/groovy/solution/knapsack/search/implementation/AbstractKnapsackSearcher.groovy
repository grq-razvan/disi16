package solution.knapsack.search.implementation

import model.knapsack.Item
import model.knapsack.Knapsack
import org.apache.commons.math3.util.ArithmeticUtils
import org.apache.commons.math3.util.FastMath
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

    protected synchronized List<Item> convertBitString(String bitString) {
        List<Item> results = new CopyOnWriteArrayList<>()
        bitString.eachWithIndex { String entry, int index ->
            if (entry == '1') {
                results.add(items.get(index))
            }
        }
        return results.toList()
    }

    protected synchronized Knapsack createKnapsack(String bitString, Integer maxWeight) {
        Knapsack result = new Knapsack(maxWeight: maxWeight)
        convertBitString(bitString).each {
            result.addItem(it)
        }
        return result
    }

    protected synchronized static String createBitString(Number number, int length) {
        StringBuffer binaryRepresentation = new StringBuffer(Long.toBinaryString(number.longValue()))
        while (binaryRepresentation.length() < length) {
            binaryRepresentation.insert(0, '0')
        }
        return binaryRepresentation.toString()
    }

    protected synchronized Map<String, Integer> adjustRuntimeParameters(Double epsilon, Integer size = items.size()) {
        Integer itemCount = size
        def temp = FastMath.log10(FastMath.sqrt(itemCount.doubleValue()) * epsilon)
        def iterations = (FastMath.cbrt((ArithmeticUtils.pow(itemCount, 2) - itemCount + 41).doubleValue())
                - FastMath.pow(temp, 2.0)) / (1 - epsilon)

        Integer iterationsInt = iterations.toInteger()
        Integer restarts = iterationsInt.intdiv(4)
        restarts > 20 ? restarts = 10 : restarts
        [
                iterations: iterationsInt,
                restarts  : restarts
        ]
    }
}