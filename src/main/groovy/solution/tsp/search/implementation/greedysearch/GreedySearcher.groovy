package solution.tsp.search.implementation.greedysearch

import model.tsp.City
import model.tsp.Route
import org.apache.commons.math3.random.RandomDataGenerator
import solution.tsp.search.implementation.AbstractTSPSearcher
import solution.tsp.search.implementation.TSPSolutionType

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
        def startTime = System.currentTimeMillis()
        List<City> temp = cities.collect()
        City startingPoint = temp.get(randomGenerator.nextInt(temp.indices.first(), temp.indices.last()))
        route.cities.add(startingPoint)
        temp.remove(startingPoint)
        while (!temp.empty) {
            City minDistanceCity = temp.min { City city ->
                startingPoint.distance(city)
            }
            route.cities.add(minDistanceCity)
            temp.remove(minDistanceCity)
            startingPoint = minDistanceCity
        }
        def endTime = System.currentTimeMillis()
        route.executionTime = endTime - startTime
        return [route]
    }
}
