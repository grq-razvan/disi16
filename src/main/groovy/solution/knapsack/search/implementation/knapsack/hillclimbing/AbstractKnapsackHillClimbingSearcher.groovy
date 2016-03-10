package solution.knapsack.search.implementation.knapsack.hillclimbing

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomData
import org.apache.commons.math3.util.ArithmeticUtils
import org.apache.commons.math3.util.FastMath
import solution.knapsack.search.implementation.AbstractKnapsackSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
abstract class AbstractKnapsackHillClimbingSearcher extends AbstractKnapsackSearcher {

    protected RandomData randomGenerator;

    protected static List<Boolean> convertFromBitString(String bitString) {
        bitString.collect { String element -> element == '1' }
    }

    protected static String convertFromBooleanRepresentation(List<Boolean> booleanList) {
        StringBuilder builder = new StringBuilder()
        booleanList.each { Boolean bit -> if (bit) builder.append('1') else builder.append('0') }
        return builder.toString()
    }

    protected static List<String> generateFlipNeighbours(String bitString) {
        List<String> neighbours = []
        List<Boolean> candidateRepresentation = convertFromBitString(bitString)
        int index = 0
        while (index < candidateRepresentation.size()) {
            List<Boolean> copy = []
            candidateRepresentation.each {
                copy.add(it)
            }
            copy.set(index, !copy.get(index))
            def neighbour = Collections.unmodifiableList(copy)
            neighbours.add(convertFromBooleanRepresentation(neighbour))
            index++
        }
        return neighbours
    }

    protected synchronized String generateRandomCandidateSolution(int length) {
        StringBuilder builder = new StringBuilder()
        while (builder.length() < length) {
            builder.append(randomGenerator.nextSecureInt(0, 1))
        }
        return builder.toString()
    }

    protected synchronized String generateRandomCandidateNeighbour(String candidateSolution) {
        List<Boolean> booleanRepresentation = convertFromBitString(candidateSolution)
        Integer randomIndexToFlip = randomGenerator.nextInt(0, booleanRepresentation.size() - 1)
        booleanRepresentation.set(randomIndexToFlip, !booleanRepresentation[randomIndexToFlip])
        return convertFromBooleanRepresentation(booleanRepresentation)
    }

    protected synchronized List<String> generateRegions(int regionCount, int size) {
        List<String> regions = []
        regionCount.times { regions.add(generateRandomCandidateSolution(size)) }
        return regions
    }

    protected
    synchronized List<Knapsack> generateValidKnapsacksFromNeighbours(Integer maxWeight, List<String> neighbours) {
        return [neighbours.collect { String candidateSolution -> createKnapsackFromBinaryString(maxWeight, candidateSolution) }
                        .findAll { Knapsack knapsack -> knapsack.isValid() }
                        .max { Knapsack knapsack -> knapsack.totalValue }]
    }

    synchronized def adjustRuntimeParameters(Double epsilon, Integer size) {
        Integer itemCount = size
        def temp = FastMath.log10(FastMath.sqrt(itemCount.doubleValue()) * epsilon)
        def iterations = (FastMath.cbrt((ArithmeticUtils.pow(itemCount, 2) - itemCount + 41).doubleValue())
                - FastMath.pow(temp, 2.0)) / (1 - epsilon)

        def restarts = iterations.toInteger().intdiv(4)
        [
                iterations: iterations.toInteger(),
                restarts  : restarts
        ]
    }
}