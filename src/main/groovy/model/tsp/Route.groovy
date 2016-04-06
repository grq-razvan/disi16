package model.tsp

/**
 *  Created by stefangrecu on 28/03/16.
 */
class Route {
    List<City> cities
    Integer maxNumber
    Number totalCost

    boolean isBetter(Route otherRoute) {
        return this.totalCost > otherRoute.totalCost
    }
}
