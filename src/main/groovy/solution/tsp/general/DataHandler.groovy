package solution.tsp.general

import generator.implementation.tsp.RandomCityGenerator
import io.converter.implementation.tsp.DataFileConverter
import io.reader.implementation.tsp.DataFileReader
import io.writer.implementation.tsp.DataFileWriter
import model.tsp.City
import solution.meta.AbstractDataHandler

/**
 *  Created by stefangrecu on 07/04/16.
 */
class DataHandler extends AbstractDataHandler<City> {

    DataHandler() {
        this.dataReader = new DataFileReader()
        this.dataWriter = new DataFileWriter()
        this.dataGenerator = new RandomCityGenerator()
        this.dataConverter = new DataFileConverter()
    }

    List<City> generateData(Integer cityCount, Number xMax = 50.0, Number yMax = 50.0) {
        return dataGenerator.generateData(
                cityCount: cityCount,
                xMax: xMax,
                yMax: yMax
        )
    }

    @Override
    void writeDataFile(List<City> data, String path) {
        def content = dataConverter.convertToMap(data)
        dataWriter.writeLines(path, content)
    }

    @Override
    Collection<City> readDataFile(String path) {
        return dataReader.getLines(path)
    }
}
