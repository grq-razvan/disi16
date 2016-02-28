package solution.knapsack.search.implementation

import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

/**
 *  Created by stefangrecu on 27/02/16.
 */
abstract class AbstractKnapsackSearcher implements ISolver<Knapsack> {

    List<Item> items
    List<Knapsack> knapsacks
    Double randomSearchParameter
    KnapsackSolutionType type

    abstract List<Knapsack> solve()

    abstract void solveInMemory()
}