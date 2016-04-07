package solution.tsp.general

/**
 *  Created by stefangrecu on 07/04/16.
 */
interface RuntimePathsContainer {
    String DATA_BASE_PATH = 'src/main/resources/tsp'
    String RESULT_BASE_PATH = 'src/main/resources/tsp/results'

    def INSTANCES_METADATA = [
            [
                    filePath      : '/eil51.tsp',
                    name          : 'eil51',
                    type          : 'tsp',
                    comment       : '51-city-problem',
                    dimension     : 51,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : '/eil75.tsp',
                    name          : 'eil76',
                    type          : 'tsp',
                    comment       : '76-city-problem',
                    dimension     : 76,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : '/eil101.tsp',
                    name          : 'eil101',
                    type          : 'tsp',
                    comment       : '101-city-problem',
                    dimension     : 101,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : '/kroC100.tsp',
                    name          : 'kroC100',
                    type          : 'tsp',
                    comment       : '100-city-problem C',
                    dimension     : 100,
                    edgeWeightType: 'EUC_2D'
            ],
            [
                    filePath      : '/ch130.tsp',
                    name          : 'ch130',
                    type          : 'tsp',
                    comment       : '130-city-problem',
                    dimension     : 130,
                    edgeWeightType: 'EUC_2D'
            ]
    ]
}