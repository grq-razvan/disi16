package application.knapsack

import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.*

/**
 * Created by stefangrecu on 12/03/16.
 */
interface RuntimeConstants {
    String PATH = 'src/main/resources/knapsack/data-'
    List<KnapsackSolutionType> CLASSIC_SOLUTIONS = [ExtensiveSearch, GreedySearch, RandomSearch]
    List<KnapsackSolutionType> HILLCLIMBING_SOLUTIONS = []
    List<Map<String, Integer>> ITEM_PARAMS = [
            [itemCount    : 10,
             maxItemWeight: 50,
             maxItemValue : 30],

            [itemCount    : 15,
             maxItemWeight: 25,
             maxItemValue : 10],

            [itemCount    : 20,
             maxItemWeight: 50,
             maxItemValue : 25],

            [itemCount    : 30,
             maxItemWeight: 60,
             maxItemValue : 100],

            [itemCount    : 50,
             maxItemWeight: 10,
             maxItemValue : 130],

            [itemCount    : 100,
             maxItemWeight: 150,
             maxItemValue : 250],

            [itemCount    : 200,
             maxItemWeight: 250,
             maxItemValue : 125],

            [itemCount    : 500,
             maxItemWeight: 100,
             maxItemValue : 50],

            [itemCount    : 1000,
             maxItemWeight: 150,
             maxItemValue : 150],

            [itemCount    : 2000,
             maxItemWeight: 200,
             maxItemValue : 50],

            [itemCount    : 5000,
             maxItemWeight: 500,
             maxItemValue : 500],

            [itemCount    : 10000,
             maxItemWeight: 100,
             maxItemValue : 1000],

            [itemCount    : 50000,
             maxItemWeight: 250,
             maxItemValue : 175],

    ]

}