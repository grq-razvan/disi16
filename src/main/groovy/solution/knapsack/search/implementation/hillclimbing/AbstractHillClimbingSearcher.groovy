package solution.knapsack.search.implementation.hillclimbing

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataGenerator
import solution.knapsack.search.implementation.AbstractKnapsackSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
abstract class AbstractHillClimbingSearcher extends AbstractKnapsackSearcher {

    protected RandomDataGenerator randomGenerator
    Map<String, Integer> runtimeParams

    protected synchronized static List<Boolean> createBooleanRepresentation(String bitString) {
        bitString.collect { String element -> element == '1' }
    }

    protected synchronized static String createBitString(List<Boolean> booleanList) {
        StringBuilder builder = new StringBuilder()
        booleanList.each { Boolean bit -> if (bit) builder.append('1') else builder.append('0') }
        return builder.toString()
    }

    private synchronized static List<String> listNeighbors(String bitString) {
        List<String> neighbours = []
        List<Boolean> candidateRepresentation = createBooleanRepresentation(bitString)
        int index = 0
        while (index < candidateRepresentation.size()) {
            List<Boolean> copy = candidateRepresentation.collect { it }
            copy[index] = !copy[index]
            neighbours.add(createBitString(copy))
            index++
        }
        return neighbours
    }

    protected synchronized String createHilltop(int length = this.items.size()) {
        StringBuffer builder = new StringBuffer()
        while (builder.length() < length) {
            builder.append(randomGenerator.nextInt(0, 1))
        }
        return builder.toString()
    }

    protected synchronized String createHilltopRandomNeighbor(String hilltop) {
        List<Boolean> booleanRepresentation = createBooleanRepresentation(hilltop)
        IntRange hilltopIndices = booleanRepresentation.indices
        int randomIndex = randomGenerator.nextInt(hilltopIndices.first(), hilltopIndices.last())
        booleanRepresentation[randomIndex] = !booleanRepresentation[randomIndex]
        return createBitString(booleanRepresentation)
    }

    protected synchronized List<String> createHilltopRegions(int regionCount, int size = this.items.size()) {
        List<String> regions = []
        regionCount.times { regions.add(createHilltop(size)) }
        return regions
    }

    protected
    synchronized List<Knapsack> createNeighborsKnapsacks(List<String> neighbours, Integer maxWeight = this.maxKnapsackWeight) {
        (neighbours.collect { String neighbor -> createKnapsack(neighbor, maxWeight) })
                .sort { Knapsack knapsack1, Knapsack knapsack2 -> knapsack2 <=> knapsack1 }
    }

    //generate 1flip neighbors
    //for each neighbor EXCEPT LAST! - because last neighbor's new neighbors
    //will always be duplicates, generate one flip neighbor again, and decrease degree
    //
    protected static synchronized List<String> getNeighbors(String solution, int degree = 1) {
        List<String> oneFlipNeighbors = listNeighbors(solution)
        List<String> result = oneFlipNeighbors
        int degreeIndex = degree

        while (degreeIndex > 1) {
            for (String neighbor in (result - result.last())) {
                result += listNeighbors(neighbor)
            }
            degreeIndex--
        }
        Set<String> resultSet = result.toSet()
        if (degreeIndex != 1) {
            (oneFlipNeighbors + solution).each { if (it in resultSet) resultSet.remove(it) }
        }
        return resultSet.asList()
    }

    protected static boolean isNeighborBetter(Knapsack current, Knapsack neighbor) {
        !current.validate() || (neighbor.validate() && neighbor.totalValue > current.totalValue)
    }

    protected Map<String, Object> steepestNeighbor(List<String> neighbors) {
        String head = neighbors.head()
        Knapsack headKnapsack = createKnapsack(head)
        for (neighbor in neighbors.tail()) {
            Knapsack tailKnapsack = createKnapsack(neighbor)
            if (isNeighborBetter(headKnapsack, tailKnapsack)) {
                head = neighbor
                headKnapsack = tailKnapsack
            }
        }
        return [hilltop: head, knapsack: headKnapsack]
    }
}