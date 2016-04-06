package solution.tsp.search.general

import generator.IDataGenerator
import generator.implementation.tsp.RandomCityGenerator
import io.converter.IDataConverter
import io.converter.implementation.tsp.DataFileConverter
import io.reader.IFileReader
import io.reader.implementation.tsp.DataFileReader
import io.writer.IFileWriter
import io.writer.implementation.tsp.DataFileWriter
import model.tsp.City

/**
 *  Created by stefangrecu on 07/04/16.
 */
class DataHandler {
    private final IFileReader<City> dataReader
    private final IFileWriter<City> dataWriter
    private final IDataGenerator<City> dataGenerator
    private final IDataConverter<City> dataConverter

    DataHandler() {
        dataReader = new DataFileReader()
        dataWriter = new DataFileWriter()
        dataGenerator = new RandomCityGenerator()
        dataConverter = new DataFileConverter()
    }

    List<City> generateData(Integer cityCount, Number xMax = 50.0, Number yMax = 50.0) {
        return dataGenerator.generateData(
                cityCount: cityCount,
                xMax: xMax,
                yMax: yMax
        )
    }

    void writeDataFile(List<City> data, String path) {
        def content = dataConverter.convertToMap(data)
        dataWriter.writeLines(path, content)
    }

    Collection<City> readDataFile(String path) {
        return dataReader.getLines(path)
    }
}
