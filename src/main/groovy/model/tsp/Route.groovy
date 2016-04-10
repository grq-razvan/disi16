package model.tsp

/**
 *  Created by stefangrecu on 28/03/16.
 */
class Route {
    List<City> cities
    Integer maxNumber
    Number executionTime

    synchronized boolean isBetter(Route otherRoute, boolean minimum = false) {
        if (minimum) {
            return totalCostMinimum < otherRoute.totalCostMinimum
        }
        return this.totalCost > otherRoute.totalCost
    }

    synchronized Number getTotalCost() {
        Number result = cities.last().distance(cities.first())
        return -(result + getTotalCostInternal(cities))
    }

    private synchronized getTotalCostInternal(List<City> cities) {
        if (cities.tail().empty || !cities.tail().head()) {
            return 0
        }
        return cities.head().distance(cities.tail().head()) + getTotalCostInternal(cities.tail())
    }

    Number getTotalCostMinimum() {
        Number result = cities.last().distance(cities.first())
        return (result + getTotalCostInternal(cities))
    }
}
