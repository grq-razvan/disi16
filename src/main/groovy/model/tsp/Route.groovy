package model.tsp

/**
 *  Created by stefangrecu on 28/03/16.
 */
class Route {
    List<City> cities
    Integer maxNumber

    boolean isBetter(Route otherRoute) {
        return this.totalCost > otherRoute.totalCost
    }

    Number getTotalCost() {
        Number result = cities.last().distance(cities.first())
        return -(result + getTotalCostInternal(cities))
    }

    private getTotalCostInternal(List<City> cities) {
        if (cities.tail().empty || !cities.tail().head()) {
            return 0
        }
        return cities.head().distance(cities.tail().head()) + getTotalCostInternal(cities.tail())
    }
}
