package solution.knapsack.search.implementation.knapsack.randomsearch

import model.knapsack.Item
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
            int randomLowerBound = expressionBase - 2 * itemsCount + LOWER_BOUND_FUNCTION_CONSTANT
            int randomUpperBound = expressionBase - 2 * itemsCount - UPPER_BOUND_FUNCTION_CONSTANT
            return Math.abs(expressionBase - numberGenerator.nextSecureInt(randomLowerBound, randomUpperBound) * (1.0 - epsilon)) / epsilon
        }
    }

    private Knapsack createSample(String pickedItems) {
        Knapsack result = new Knapsack(maxWeight: knapsacks[0].maxWeight, items: [])
        pickedItems.eachWithIndex { String element, int i ->
            if (element == '1') {
                Item item = items.get(i)
                result.addItem(item)
            }
        }
        return result
    }

    private List<Knapsack> computeSolution() {
        List<Knapsack> samples = []
        int numberOfIterations = adjustRuntime(this.randomSearchParameter)
        for (i in (0..numberOfIterations - 1)) {
            String candidate = generateRandomBinaryString(items.size())
            def sample = createSample(candidate)
            samples.add(sample)
        }
        return samples
    }

    private static List<Knapsack> getBestResults(List<Knapsack> samples) {
        samples.sort {
            knapsack1, knapsack2 -> knapsack2.totalValue <=> knapsack1.totalValue
        }.findAll {
            it.currentWeight < it.maxWeight
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
            return [getBestResults(results).first()]
        } else {
            def bestCandidates = getBestResults(results)
            return [bestCandidates.first()] + getAverageResult(bestCandidates)
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
