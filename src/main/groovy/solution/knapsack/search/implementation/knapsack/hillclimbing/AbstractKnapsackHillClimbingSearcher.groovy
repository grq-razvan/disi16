package solution.knapsack.search.implementation.knapsack.hillclimbing

import solution.knapsack.search.implementation.AbstractKnapsackSearcher

/**
 *  Created by stefangrecu on 06/03/16.
 */
abstract class AbstractKnapsackHillClimbingSearcher extends AbstractKnapsackSearcher {

    protected static List<Boolean> convertFromBitString(String bitString) {
        bitString.collect { String element ->
            element == '1'
        }
    }

    protected static String convertFromBooleanRepresentation(List<Boolean> booleanList) {
        StringBuilder builder = new StringBuilder()
        booleanList.each { Boolean bit ->
            if (bit) builder.append('1') else builder.append('0')
        }
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
        println neighbours
        return neighbours
    }


}