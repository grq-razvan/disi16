package solution.tsp.search.implementation

import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator

/**
 *  Created by stefangrecu on 06/04/16.
 */
class LocalGreedySearcher extends AbstractTSPSearcher {

    LocalGreedySearcher(Integer maxNumber) {
        this.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.Mix
        this.maxNumber = maxNumber
        super.runtimeParams.iterations = (maxNumber * 1.5).toInteger()
    }

    @Override
    List<Route> solve() {
        return null
    }
}
