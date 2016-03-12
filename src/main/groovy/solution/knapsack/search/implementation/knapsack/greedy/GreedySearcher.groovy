package solution.knapsack.search.implementation.knapsack.greedy

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.knapsack.search.implementation.AbstractKnapsackSearcher

import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.Greedy

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
            (i2.value / (i2.weight == 0 ? 1 : i2.weight)) / (i1.value / (i1.weight == 0 ? 1 : i1.weight))
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
