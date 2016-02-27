package solution.knapsack.search

import model.knapsack.Knapsack
import solution.ISolver

/**
 * Created by stefangrecu on 27/02/16.
 */
interface IKnapsackSearch extends ISolver<Knapsack> {

    List<Knapsack> solve()

    void solveInMemory()
}