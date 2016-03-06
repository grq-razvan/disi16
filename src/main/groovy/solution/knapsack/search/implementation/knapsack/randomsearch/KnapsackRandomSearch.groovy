package solution.knapsack.search.implementation.knapsack.randomsearch

import model.knapsack.Knapsack
import org.apache.commons.lang3.math.NumberUtils
import org.apache.commons.math3.random.RandomData
import org.apache.commons.math3.random.RandomDataImpl
import org.apache.commons.math3.util.ArithmeticUtils
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 28/02/16.
 */
class KnapsackRandomSearch extends AbstractKnapsackSearcher {

    private final RandomData numberGenerator
    private static final LOWER_BOUND_FUNCTION_CONSTANT = 10
    private static final UPPER_BOUND_FUNCTION_CONSTANT = 10

    KnapsackRandomSearch(Integer knapsackMaxWeight, Double randomEpsilon) {
        this.knapsacks = [new Knapsack(maxWeight: knapsackMaxWeight, items: [])]
        this.items = []
        this.randomSearchParameter = randomEpsilon
        this.type = KnapsackSolutionType.RandomSearch
        this.numberGenerator = new RandomDataImpl()
    }

    private String generateRandomBinaryString(int length) {
        StringBuilder builder = new StringBuilder()
        while (builder.length() < length) {
            builder.append(numberGenerator.nextSecureInt(0, 1))
        }
        return builder.toString()
    }

    private Integer adjustRuntime(Double epsilon) {
        if (epsilon == NumberUtils.createDouble("1.0")) {
            return 1
        } else if (epsilon == NumberUtils.createDouble("0.0")) {
            int itemsCount = items.size()
            return ArithmeticUtils.pow(2, itemsCount)
        } else {
            int itemsCount = items.size()
            int expressionBase = ArithmeticUtils.pow(itemsCount, 2)
            int randomBase = expressionBase - 2 * itemsCount
            int randomUpperBound = randomBase + UPPER_BOUND_FUNCTION_CONSTANT
            int randomLowerBound = randomBase - LOWER_BOUND_FUNCTION_CONSTANT
            return Math.abs(expressionBase - numberGenerator.nextSecureInt(randomLowerBound, randomUpperBound) * (1.0 - epsilon)) / epsilon
        }
    }

    private List<Knapsack> computeSolution() {
        List<Knapsack> samples = []
        int numberOfIterations = adjustRuntime(this.randomSearchParameter)
        numberOfIterations.times {
            samples.add(createKnapsackFromBinaryString(this.knapsacks[0].maxWeight, generateRandomBinaryString(items.size())))
        }
        return samples
    }

    private static List<Knapsack> getBestResults(List<Knapsack> samples) {
        samples.sort {
            knapsack1, knapsack2 -> knapsack2.totalValue <=> knapsack1.totalValue
        }.findAll {
            it.valid
        }
    }

    public static List<Knapsack> getAverageResult(List<Knapsack> samples) {
        def occurenceMap = [:]
        for (it in samples) {
            def key = it.totalValue
            if (occurenceMap.containsKey(key)) {
                def value = occurenceMap.get(key)
                value++
                occurenceMap.put(key, value)
            } else {
                occurenceMap.put(key, 1)
            }
        }
        return [samples.find {
            it.totalValue == occurenceMap.max { it.value }.key
        }]
    }

    @Override
    List<Knapsack> solve() {
        List<Knapsack> results = computeSolution()
        if (this.randomSearchParameter == NumberUtils.createDouble("0.0") || this.randomSearchParameter == NumberUtils.createDouble("1.0")) {
            return getBestResults(results).empty ? [] : [getBestResults(results).first()]
        } else {
            def bestCandidates = getBestResults(results)
            return bestCandidates.empty ? [] : [bestCandidates.first()] + getAverageResult(bestCandidates)
        }
    }

    @Override
    void solveInMemory() {
        List<Knapsack> results = computeSolution()
        if (this.randomSearchParameter == NumberUtils.createDouble("0.0") || this.randomSearchParameter == NumberUtils.createDouble("1.0")) {
            knapsacks = [getBestResults(results).first()]
        } else {
            def bestCandidates = getBestResults(results)
            knapsacks = [bestCandidates.first()] + getAverageResult(bestCandidates)
        }
    }
}
