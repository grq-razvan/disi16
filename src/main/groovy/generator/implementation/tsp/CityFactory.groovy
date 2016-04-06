package generator.implementation.tsp

import generator.IFactory
import model.tsp.City

/**
 *  Created by stefangrecu on 06/04/16.
 */
class CityFactory implements IFactory<City> {

    @Override
    City create(Map<String, Object> params) {
        Number firstCoordinate = params.x as Number
        Number secondCoordinate = params.y as Number

        return new City(
                coordinates: new Tuple2<Number, Number>(firstCoordinate, secondCoordinate)
        )
    }
}
