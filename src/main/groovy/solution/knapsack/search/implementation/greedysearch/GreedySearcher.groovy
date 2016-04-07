package solution.knapsack.search.implementation.greedysearch

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.knapsack.search.implementation.AbstractKnapsackSearcher

import static solution.knapsack.search.implementation.KnapsackSolutionType.Greedy

/**
 *  Created by stefangrecu on 27/02/16.
 */
class GreedySearcher extends AbstractKnapsackSearcher {

    GreedySearcher(Integer maxKnapsackWeight) {
        this.type = Greedy
        this.maxKnapsackWeight = maxKnapsackWeight
    }

    @Override
    List<Knapsack> solve() {
        return [computeSolution()]
    }

    private Knapsack computeSolution() {
        def greedyRatio = { Item i1, Item i2 ->
            int i1Ratio = (i1.value.intdiv(i1.weight == 0 ? 1 : i1.weight))
            int i2Ratio = (i2.value.intdiv(i2.weight == 0 ? 1 : i2.weight))
            i2Ratio <=> i1Ratio
        }
        Knapsack knapsack = new Knapsack(maxWeight: this.maxKnapsackWeight)
        items.sort(greedyRatio).each { Item item ->
            if (item.weight + knapsack.currentWeight <= knapsack.maxWeight) {
                knapsack.addItem(item)
            }
        }
        Item maxValuedItem = items.max { Item item -> item.value }
        if (knapsack.totalValue < maxValuedItem.value && maxValuedItem.weight <= knapsack.maxWeight) {
            knapsack.clearKnapsack()
            knapsack.addItem(maxValuedItem)
        }
        return knapsack
    }
}
