package application.knapsack

import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType

import static solution.knapsack.search.implementation.knapsack.KnapsackSolutionType.*

/**
 *  Created by stefangrecu on 12/03/16.
 *
 * @field CLASSIC  used to differentiate the type of methods to use. See {HILL_CLIMBING}.
 *
 * @field PATH used when generating the path to access and read data files.
 *
 * @field HILL_CLIMBING used to differentiate the type of methods to use on a data set. See {CLASSIC}.
 *
 * @field CLASSIC_SOLUTIONS used to select which classic algorithms to use on a data set.
 *
 * @field HILL_CLIMBING_SOLUTIONS used to select which hill climbing methods to use on a data set.
 *
 * @field BASE_KNAPSACK_WEIGHT the initial knapsack maximum weight. Adjusted proportionately when data
 * set has much more items.
 *
 * @field BASE_ITEM_WEIGHT the initial maximum weight of an item.
 *
 * @field BASE_ITEM_VALUE the initial maximum value of an item.
 *
 * @field ITEM_PARAMS a list of parameters used to generate test data files. Contains the item count,
 * maximum item value and maximum item weight.
 *
 * @field CLASSIC_TEST_CASES a list of parameters used to describe test cases. Contains the maximum
 * knapsack weight, the item count, the solution type, and the adaptive random certainty factor.
 *
 * @field HILL_CLIMBING_TEST_CASES a list of parameters used to describe each test case using
 * hill-climbing. Contains the maximum knapsack weight, the item count,
 * the solution type - classic of hillclimbing, the adaptive random certainty, the number of regions
 * and the neighborhood degree.
 */

interface RuntimeConfiguration {
    String CLASSIC = 'classic'
    String PATH = 'src/main/resources/knapsack/data-'
    String HILL_CLIMBING = 'hill-climbing'
    List<KnapsackSolutionType> CLASSIC_SOLUTIONS = [Exhaustive, Greedy, Stochastic]
    List<KnapsackSolutionType> HILL_CLIMBING_SOLUTIONS = [SteepestAscent, RandomHillClimbing]
    int BASE_KNAPSACK_WEIGHT = 100
    int BASE_ITEM_WEIGHT = 50
    int BASE_ITEM_VALUE = 30

    List<Map<String, Integer>> ITEM_PARAMS = [
            [itemCount    : 10,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 15,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 20,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 30,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 50,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 100,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 200,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 500,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 1000,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 2000,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 5000,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 10000,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 50000,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE],

            [itemCount    : 100000,
             maxItemWeight: BASE_ITEM_WEIGHT,
             maxItemValue : BASE_ITEM_VALUE]

    ]

    List<Map<String, Object>> CLASSIC_TEST_CASES = [
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT,
                    itemCount     : 10,
                    type          : CLASSIC,
                    certainty     : 0.95

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 1.5,
                    itemCount     : 15,
                    type          : CLASSIC,
                    certainty     : 0.94

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 2,
                    itemCount     : 20,
                    type          : CLASSIC,
                    certainty     : 0.93

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 5,
                    itemCount     : 50,
                    type          : CLASSIC,
                    certainty     : 0.92

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 10,
                    itemCount     : 100,
                    type          : CLASSIC,
                    certainty     : 0.91

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 20,
                    itemCount     : 200,
                    type          : CLASSIC,
                    certainty     : 0.89

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 200,
                    itemCount     : 1000,
                    type          : CLASSIC,
                    certainty     : 0.80

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 400,
                    itemCount     : 2000,
                    type          : CLASSIC,
                    certainty     : 0.75

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 800,
                    itemCount     : 5000,
                    type          : CLASSIC,
                    certainty     : 0.70

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 2500,
                    itemCount     : 10000,
                    type          : CLASSIC,
                    certainty     : 0.65

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 8000,
                    itemCount     : 50000,
                    type          : CLASSIC,
                    certainty     : 0.60

            ],
            [knapsackWeight: BASE_KNAPSACK_WEIGHT * 15000,
             itemCount     : 100000,
             type          : CLASSIC,
             certainty     : 0.55
            ]

    ]

    List<Map<String, Object>> HILL_CLIMBING_TEST_CASES = [
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT,
                    itemCount     : 10,
                    type          : HILL_CLIMBING,
                    certainty     : 0.95,
                    regions       : 3,
                    degree        : 1,

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 1.5,
                    itemCount     : 15,
                    type          : HILL_CLIMBING,
                    certainty     : 0.94,
                    regions       : 4,
                    degree        : 2

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 2,
                    itemCount     : 20,
                    type          : HILL_CLIMBING,
                    certainty     : 0.93,
                    regions       : 5,
                    degree        : 2


            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 5,
                    itemCount     : 50,
                    type          : HILL_CLIMBING,
                    certainty     : 0.91,
                    regions       : 4,
                    degree        : 3

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 10,
                    itemCount     : 100,
                    type          : HILL_CLIMBING,
                    certainty     : 0.85,
                    regions       : 6,
                    degree        : 2

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 20,
                    itemCount     : 200,
                    type          : HILL_CLIMBING,
                    certainty     : 0.8,
                    regions       : 7,
                    degree        : 2

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 200,
                    itemCount     : 1000,
                    type          : HILL_CLIMBING,
                    certainty     : 0.75,
                    regions       : 10,
                    degree        : 1

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 400,
                    itemCount     : 2000,
                    type          : HILL_CLIMBING,
                    certainty     : 0.7,
                    regions       : 16,
                    degree        : 2

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 800,
                    itemCount     : 5000,
                    type          : HILL_CLIMBING,
                    certainty     : 0.65,
                    regions       : 12,
                    degree        : 3

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 2500,
                    itemCount     : 10000,
                    type          : HILL_CLIMBING,
                    certainty     : 0.60,
                    regions       : 20,
                    degree        : 4

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 8000,
                    itemCount     : 50000,
                    type          : HILL_CLIMBING,
                    certainty     : 0.55,
                    regions       : 50,
                    degree        : 5

            ],
            [
                    knapsackWeight: BASE_KNAPSACK_WEIGHT * 15000,
                    itemCount     : 100000,
                    type          : HILL_CLIMBING,
                    certainty     : 0.25,
                    regions       : 500,
                    degree        : 8

            ]
    ]


}