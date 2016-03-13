package application.knapsack

import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.*

/**
 *  Created by stefangrecu on 12/03/16.
 */
interface RuntimeConstants {
    String CLASSIC = 'classic'
    String PATH = 'src/main/resources/knapsack/data-'
    String HILL_CLIMBING = 'hill-climbing'
    List<KnapsackSolutionType> CLASSIC_SOLUTIONS = [Exhaustive, Greedy, Stochastic]
    List<KnapsackSolutionType> HILL_CLIMBING_SOLUTIONS = [Experiment, SteepestAscent, NextAscent, RandomHillClimbing]

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

    List<Map<String, Object>> CLASSIC_TEST_CASES = [
            [
                    knapsackWeight: 100,
                    itemCount     : 10,
                    type          : CLASSIC,
                    certainty     : 0.92

            ],
            [
                    knapsackWeight: 50,
                    itemCount     : 15,
                    type          : CLASSIC,
                    certainty     : 0.85

            ],
            [
                    knapsackWeight: 125,
                    itemCount     : 20,
                    type          : CLASSIC,
                    certainty     : 0.51

            ],
//            [
//                    knapsackWeight: 250,
//                    itemCount     : 50,
//                    type          : CLASSIC,
//                    certainty     : 0.91
//
//            ],
//            [
//                    knapsackWeight: 750,
//                    itemCount     : 100,
//                    type          : CLASSIC,
//                    certainty     : 0.49
//
//            ],
//            [
//                    knapsackWeight: 1500,
//                    itemCount     : 200,
//                    type          : CLASSIC,
//                    certainty     : 0.35
//
//            ],
//            [
//                    knapsackWeight: 10000,
//                    itemCount     : 1000,
//                    type          : CLASSIC,
//                    certainty     : 0.6
//
//            ],
//            [
//                    knapsackWeight: 30000,
//                    itemCount     : 2000,
//                    type          : CLASSIC,
//                    certainty     : 0.4
//
//            ],
//            [
//                    knapsackWeight: 40000,
//                    itemCount     : 5000,
//                    type          : CLASSIC,
//                    certainty     : 0.37
//
//            ],
//            [
//                    knapsackWeight: 50000,
//                    itemCount     : 10000,
//                    type          : CLASSIC,
//                    certainty     : 0.33
//
//            ],
//            [
//                    knapsackWeight: 50000,
//                    itemCount     : 50000,
//                    type          : CLASSIC,
//                    certainty     : 0.31
//
//            ],
//            [
//                    knapsackWeight: 80000,
//                    itemCount     : 100000,
//                    type          : CLASSIC,
//                    certainty     : 0.25
//
//            ]
    ]

    List<Map<String, Object>> HILL_CLIMBING_TEST_CASES = [
            [
                    knapsackWeight: 100,
                    itemCount     : 10,
                    type          : HILL_CLIMBING,
                    certainty     : 0.92,
                    regions       : 3,
                    degree        : 1,

            ],
            [
                    knapsackWeight: 50,
                    itemCount     : 15,
                    type          : HILL_CLIMBING,
                    certainty     : 0.85,
                    regions       : 4,
                    degree        : 2

            ],
            [
                    knapsackWeight: 125,
                    itemCount     : 20,
                    type          : HILL_CLIMBING,
                    certainty     : 0.51,
                    regions       : 2,
                    degree        : 2


            ],
//            [
//                    knapsackWeight: 250,
//                    itemCount     : 50,
//                    type          : CLASSIC,
//                    certainty     : 0.91
//
//            ],
//            [
//                    knapsackWeight: 750,
//                    itemCount     : 100,
//                    type          : CLASSIC,
//                    certainty     : 0.49
//
//            ],
//            [
//                    knapsackWeight: 1500,
//                    itemCount     : 200,
//                    type          : CLASSIC,
//                    certainty     : 0.35
//
//            ],
//            [
//                    knapsackWeight: 10000,
//                    itemCount     : 1000,
//                    type          : CLASSIC,
//                    certainty     : 0.6
//
//            ],
//            [
//                    knapsackWeight: 30000,
//                    itemCount     : 2000,
//                    type          : CLASSIC,
//                    certainty     : 0.4
//
//            ],
//            [
//                    knapsackWeight: 40000,
//                    itemCount     : 5000,
//                    type          : CLASSIC,
//                    certainty     : 0.37
//
//            ],
//            [
//                    knapsackWeight: 50000,
//                    itemCount     : 10000,
//                    type          : CLASSIC,
//                    certainty     : 0.33
//
//            ],
//            [
//                    knapsackWeight: 50000,
//                    itemCount     : 50000,
//                    type          : CLASSIC,
//                    certainty     : 0.31
//
//            ],
//            [
//                    knapsackWeight: 80000,
//                    itemCount     : 100000,
//                    type          : CLASSIC,
//                    certainty     : 0.25
//
//            ]
    ]


}