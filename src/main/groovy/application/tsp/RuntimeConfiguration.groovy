package application.tsp

/**
 *  Created by stefangrecu on 06/04/16.
 */
interface RuntimeConfiguration {
    int EIL_51 = 0, EIL_76 = 1, EIL_101 = 2, KROC_100 = 3, CH_130 = 4
    def INSTANCES_METADATA = [
            [
                    filePath      : 'src/main/resources/tsp/eil51.tsp',
                    name          : 'eil51',
                    type          : 'tsp',
                    comment       : '51-city-problem',
                    dimension     : 51,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : 'src/main/resources/tsp/eil75.tsp',
                    name          : 'eil76',
                    type          : 'tsp',
                    comment       : '76-city-problem',
                    dimension     : 76,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : 'src/main/resources/tsp/eil101.tsp',
                    name          : 'eil101',
                    type          : 'tsp',
                    comment       : '101-city-problem',
                    dimension     : 101,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : 'src/main/resources/tsp/kroC100.tsp',
                    name          : 'kroC100',
                    type          : 'tsp',
                    comment       : '100-city-problem C',
                    dimension     : 100,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : 'src/main/resources/tsp/ch130.tsp',
                    name          : 'ch130',
                    type          : 'tsp',
                    comment       : '130-city-problem',
                    dimension     : 130,
                    edgeWeightType: 'EUC_2D'
            ]
    ]
}
