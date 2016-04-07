package solution.knapsack.search.implementation

import model.knapsack.Item
import model.knapsack.Knapsack
import org.apache.commons.math3.util.ArithmeticUtils
import org.apache.commons.math3.util.FastMath
import solution.ISolver

import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 27/02/16.
 */
abstract class AbstractKnapsackSearcher implements ISolver<Knapsack> {

    List<Item> items = []
    Integer maxKnapsackWeight
    Double adaptiveRandomQuality
    KnapsackSolutionType type

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

    protected synchronized Knapsack createKnapsack(String bitString, Integer maxWeight = this.maxKnapsackWeight) {
        Knapsack result = new Knapsack(maxWeight: maxWeight)
        convertBitString(bitString).each {
            result.addItem(it)
        }
        return result
    }

    protected synchronized String createBitString(Number number, int length = this.items.size()) {
        StringBuffer binaryRepresentation = new StringBuffer(Long.toBinaryString(number.longValue()))
        while (binaryRepresentation.length() < length) {
            binaryRepresentation.insert(0, '0')
        }
        return binaryRepresentation.toString()
    }

    protected
    synchronized Map<String, Integer> adjustRuntimeParameters(Double epsilon = this.adaptiveRandomQuality, Integer size = this.items.size()) {
        Integer itemCount = size
        if (itemCount >= 30000) {
            return [
                    iterations: 50,
                    restarts  : 5
            ]
        }

        if (itemCount >= 5000) {
            return [iterations: 80,
                    restarts  : 8
            ]
        }

        if (itemCount >= 1500) {
            return [iterations: 500,
                    restarts  : 10
            ]
        }

        if (itemCount >= 200) {
            return [iterations: 100,
                    restarts  : 10
            ]
        }
        def temp = FastMath.log10(FastMath.sqrt(itemCount.doubleValue()) * epsilon)
        def iterations = (FastMath.cbrt((ArithmeticUtils.pow(itemCount, 2) - itemCount + 41).doubleValue())
                - FastMath.pow(temp, 2.0)) / (1 - epsilon)

        Integer iterationsInt = iterations.toInteger()
        Integer restarts = iterationsInt.intdiv(4)
        if (restarts > 100) {
            restarts = restarts.intdiv(4)
        }
        [
                iterations: iterationsInt,
                restarts  : restarts
        ]
    }
}