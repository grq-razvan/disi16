package solution.knapsack.search.implementation.knapsack.greedy

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 27/02/16.
 */
class KnapsackGreedySearch extends AbstractKnapsackSearcher {

    KnapsackGreedySearch(Integer knapsackMaxWeight) {
        this.items = []
        this.knapsacks = [new Knapsack(maxWeight: knapsackMaxWeight, items: [])]
        this.type = KnapsackSolutionType.GreedySearch
    }

    @Override
    List<Knapsack> solve() {
        return computeSolution()
    }

    @Override
    void solveInMemory() {
        this.knapsacks = computeSolution()
    }

    private List<Knapsack> computeSolution() {
        Knapsack knapsack = new Knapsack(maxWeight: this.knapsacks[0].maxWeight, items: [])
        List<Item> heuristicallySortedItems = items.sort { Item a, Item b ->
            (b.value / (b.weight == 0 ? 1 : b.weight)) <=> (a.value / (a.weight == 0 ? 1 : a.weight))
        }
        heuristicallySortedItems.each { Item item ->
            if (item.weight + knapsack.currentWeight <= knapsack.maxWeight) {
                knapsack.addItem(item)
            }
        }

        Item maxValuedItem = items.max { Item item -> item.value }
        if (knapsack.totalValue < maxValuedItem.value && maxValuedItem.weight < knapsack.maxWeight) {
            knapsack.clearKnapsack()
            knapsack.addItem(maxValuedItem)
        }
        return [knapsack]
    }

}
