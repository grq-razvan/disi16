package model.tsp

import org.apache.commons.math3.ml.distance.EuclideanDistance

/**
 *  Created by stefangrecu on 24/03/16.
 */
class City {
    Tuple2<Number, Number> coordinates

    Number distance(City otherCity) {
        EuclideanDistance distance = new EuclideanDistance()
        Number result = distance.compute([coordinates.first.doubleValue(), coordinates.second.doubleValue()].toArray() as double[],
                [otherCity.coordinates.first.doubleValue(), otherCity.coordinates.second.doubleValue()].toArray() as double[]);
        return result ?: 0
    }
}
