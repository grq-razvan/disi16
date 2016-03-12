package solution.knapsack.search.implementation.knapsack.greedy

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 27/02/16.
 */
class GreedySearcher extends AbstractKnapsackSearcher {

    GreedySearcher(Integer maxKnapsackWeight) {
        this.type = KnapsackSolutionType.GreedySearch
        this.maxKnapsackWeight = maxKnapsackWeight
    }

    @Override
    List<Knapsack> solve() {
        return [computeSolution()]
    }

    private Knapsack computeSolution() {
        def greedyRatio = { Item item1, Item item2 -> (item2.value / (item2.weight == 0 ? 1 : item2.weight)) / (item1.value / (item1.weight == 0 ? 1 : item1.weight)) }
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
