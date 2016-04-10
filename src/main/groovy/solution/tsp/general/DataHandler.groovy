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

    @Override
    List<City> generateData(Map data) {
        return dataGenerator.generateData(
                cityCount: data.cityCount,
                xMax: data.xMax ?: 50,
                yMax: data.yMax ?: 50
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
