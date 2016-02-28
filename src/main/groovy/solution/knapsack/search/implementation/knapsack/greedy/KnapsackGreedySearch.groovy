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
        knapsacks = computeSolution()
    }

    private List<Knapsack> computeSolution() {
        Knapsack knapsack = new Knapsack(maxWeight: this.knapsacks[0].maxWeight, items: this.knapsacks[0].items)
        List<Item> heuristicallySortedItems = items.sort {
            Item a, Item b -> (b.value / b.weight) <=> (a.value / a.weight)
        }
        heuristicallySortedItems.each { Item item ->
            if (item.weight < knapsack.maxWeight - knapsack.currentWeight) {
                knapsack.addItem(item)
            }
        }

        Item item = items.max { Item item -> item.value }
        if (knapsack.totalValue < item.value && item.weight < knapsack.maxWeight) {
            knapsack.clearKnapsack()
            knapsack.addItem(item)
        }
        return [knapsack]
    }

}
