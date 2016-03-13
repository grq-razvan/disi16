package solution.knapsack.search.implementation.knapsack.randomsearch

import model.knapsack.Knapsack
import org.apache.commons.math3.random.RandomDataGenerator
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 28/02/16.
 */
class RandomSearcher extends AbstractKnapsackSearcher {

    private final RandomDataGenerator numberGenerator
    private Map<String, Number> runtimeParams

    RandomSearcher(Integer maxKnapsackWeight, Double randomEpsilon) {
        this.type = KnapsackSolutionType.Stochastic
        this.maxKnapsackWeight = maxKnapsackWeight
        this.adaptiveRandomQuality = randomEpsilon
        this.numberGenerator = new RandomDataGenerator()
        this.runtimeParams = [:]
    }

    @Override
    List<Knapsack> solve() {
        runtimeParams = adjustRuntimeParameters(adaptiveRandomQuality)
        return computeSolution(runtimeParams)
    }

    private List<Knapsack> computeSolution(Map<String, Number> runtimeParams) {
        List<Knapsack> bestResults = []
        List<Knapsack> avgResults = []
        runtimeParams.restarts.times {
            List<Knapsack> samples = createSamples(runtimeParams.iterations)
            bestResults += (samples.findAll { Knapsack knapsack -> knapsack.validate() }).max { it.totalValue }
            avgResults += getAverageResult(samples)
        }
        List<Knapsack> results = bestResults + avgResults
        if (results.empty) {
            return []
        }
        return (results.findAll {
            if (it) {
                it.validate()
            }
        }).sort { k1, k2 -> k2.totalValue <=> k1.totalValue }.take(5)
    }

    private List<Knapsack> createSamples(Number iterations) {
        List<Knapsack> samples = []
        iterations.times {
            String sampleData = generateRandomBinaryString(items.size())
            Knapsack knapsack = createKnapsack(sampleData, this.maxKnapsackWeight)
            samples += knapsack
        }
        return samples
    }

    private static List<Knapsack> getAverageResult(List<Knapsack> samples) {
        def occurrencesMap = [:]
        for (it in samples) {
            def key = it.totalValue
            if (occurrencesMap.containsKey(key)) {
                def value = occurrencesMap.get(key)
                value++
                occurrencesMap.put(key, value)
            } else {
                occurrencesMap.put(key, 1)
            }
        }
        for (sample in samples) {
            if (sample.totalValue == occurrencesMap.max { it.value }.key) {
                return [sample]
            }
        }
        return []
    }

    private String generateRandomBinaryString(int length) {
        StringBuilder builder = new StringBuilder()
        while (builder.length() < length) {
            builder.append(numberGenerator.nextInt(0, 1))
        }
        return builder.toString()
    }


}
