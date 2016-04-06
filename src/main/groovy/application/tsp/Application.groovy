package application.tsp

import model.tsp.City
import solution.tsp.search.AbstractTSPSearcher

/**
 *  Created by stefangrecu on 24/03/16.
 */
class Application {
    public static void main(String[] args) {
        City city = new City(coordinates: new Tuple2(5, 10))
        City otherCity = new City(coordinates: new Tuple2(3, 12))
        System.out.println(city.distance(otherCity))
        AbstractTSPSearcher tsp = new AbstractTSPSearcher()
        tsp.generatePermutations(10).each { println it }
    }
}
