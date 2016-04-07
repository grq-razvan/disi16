package solution.tsp.search.implementation

import model.tsp.City
import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator

/**
 *  Created by stefangrecu on 06/04/16.
 */
class GreedySearcher extends AbstractTSPSearcher {

    GreedySearcher(Integer cityCount) {
        super.randomGenerator = new RandomDataGenerator()
        this.maxNumber = cityCount
        this.solutionType = TSPSolutionType.Greedy
    }

    @Override
    List<Route> solve() {
        return solveInternal(runtimeParams)
    }

    private List<Route> solveInternal(Map params) {
        Route route = new Route(cities: [], maxNumber: super.maxNumber)
        List<City> temp = cities.collect()
        City startingPoint = temp.get(randomGenerator.nextInt(temp.indices.first(), temp.indices.last()))
        route.cities.add(startingPoint)
        while (!temp.empty) {
            City minDistanceCity = temp.min { City city ->
                startingPoint.distance(city)
            }
            route.cities.add(minDistanceCity)
            temp.remove(minDistanceCity)
            startingPoint = minDistanceCity
        }
        return [route]
    }
}
