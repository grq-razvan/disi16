package solution.tsp.search.implementation

import model.tsp.Route

/**
 *  Created by stefangrecu on 06/04/16.
 */
class LocalSearcher extends AbstractTSPSearcher {

    @Override
    List<Route> solve() {
        return solveInternal()
    }

    private static List<Route> solveInternal() {
        return []
    }
}
