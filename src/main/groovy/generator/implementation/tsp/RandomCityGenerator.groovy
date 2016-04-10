package generator.implementation.tsp

import generator.IDataGenerator
import generator.IFactory
import model.tsp.City
import org.apache.commons.math3.random.RandomDataGenerator

/**
 *  Created by stefangrecu on 07/04/16.
 */
class RandomCityGenerator implements IDataGenerator<City> {

    private final RandomDataGenerator numberGenerator
    private final IFactory<City> cityFactory

    RandomCityGenerator() {
        this.numberGenerator = new RandomDataGenerator()
        this.cityFactory = new CityFactory()
    }

    @Override
    List<City> generateData(Map<String, Number> params) {
        Double xMax = params.xMax ?: 50.0
        Double yMax = params.yMax ?: 50.0
        Integer cityCount = params.cityCount
        return Collections.unmodifiableList(
                (0..<cityCount).collect {
                    Double currentX = numberGenerator.nextUniform(0.1, xMax)
                    Double currentY = numberGenerator.nextUniform(0.1, yMax)
                    cityFactory.create(
                            [
                                    x: currentX.toInteger(),
                                    y: currentY.toInteger()
                            ]
                    )
                }
        )
    }
}
