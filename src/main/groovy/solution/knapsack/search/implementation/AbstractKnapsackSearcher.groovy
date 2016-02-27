package solution.knapsack.search.implementation

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver

/**
 * Created by stefangrecu on 27/02/16.
 */
abstract class AbstractKnapsackSearcher implements ISolver<Knapsack> {

    List<Item> items
    List<Knapsack> knapsacks
    Integer randomSearchParameter

    abstract List<Knapsack> solve()

    abstract void solveInMemory()
}