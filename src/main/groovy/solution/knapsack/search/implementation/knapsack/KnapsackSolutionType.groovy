package solution.knapsack.search.implementation.knapsack

/**
 * Created by stefangrecu on 27/02/16.
 */
enum KnapsackSolutionType {
    ExtensiveSearch(0),
    RandomSearch(1),
    GreedySearch(2)

    Integer orderNumber

    KnapsackSolutionType(Integer orderNumber) {
        this.orderNumber = orderNumber
    }
}