package solution.tsp.search.implementation

import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator

import java.util.concurrent.CopyOnWriteArrayList

/**
 *  Created by stefangrecu on 06/04/16.
 */
class LocalGreedySearcher extends AbstractTSPSearcher {

    LocalGreedySearcher(Integer maxNumber) {
        super.randomGenerator = new RandomDataGenerator()
        this.solutionType = TSPSolutionType.Mix
        this.maxNumber = maxNumber
        this.runtimeParams.iterations = (maxNumber * 1.5).toInteger()
        this.runtimeParams.restarts = (this.runtimeParams.iterations as Integer).intdiv(10).intValue()
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        CopyOnWriteArrayList<Route> routes = []
        (1..params.restarts).eachParallel {
            Route route = new Route(cities: [], maxNumber: this.maxNumber)
            initRoute(route, Collections.synchronizedList(cities), this.maxNumber)
            params.iterations.times {
                CopyOnWriteArrayList<Route> swaps = []
                CopyOnWriteArrayList<Route> moves = []
                int startIndex = route.cities.indices.first()
                int lastIndex = route.cities.indices.last()
                for (int i = startIndex; i < lastIndex - 1; i++) {
                    for (int j = i + 1; j < lastIndex; j++) {
                        def otherRoute = create2SwapRoute(route, i, j)
                        if (otherRoute.isBetter(route)) {
                            swaps.add(otherRoute)
                            route = otherRoute
                        }
                    }
                    def randomShift = randomGenerator.nextInt(0, 1)
                    def otherRoute = create3MoveRoute(route, i, 3, randomShift)
                    if (otherRoute.isBetter(swaps.max { it.totalCost })) {
                        moves.add(otherRoute)
                        route = otherRoute
                    }

                }
                def temp = swaps + moves
                routes += temp
            }
        }
        return routes.sort { a, b -> b.totalCost <=> a.totalCost }.take(3)
    }
}
