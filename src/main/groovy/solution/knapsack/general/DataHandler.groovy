package solution.knapsack.general

import generator.IDataGenerator
import generator.implementation.knapsack.RandomItemGenerator
import io.converter.IDataConverter
import io.converter.implementation.knapsack.DataFileConverter
import io.reader.IFileReader
import io.reader.implementation.knapsack.DataFileReader
import io.writer.IFileWriter
import io.writer.implementation.knapsack.DataFileWriter
import model.knapsack.Item

/**
 *  Created by stefangrecu on 25/02/16.
 */
class DataHandler {

    private final IFileReader<Item> dataReader
    private final IFileWriter<Item> dataWriter
    private final IDataGenerator<Item> dataGenerator
    private final IDataConverter<Item> dataConverter

    DataHandler() {
        dataReader = new DataFileReader()
        dataWriter = new DataFileWriter()
        dataGenerator = new RandomItemGenerator()
        dataConverter = new DataFileConverter()
    }

    List<Item> generateData(Integer itemCount, Integer maxWeight = 10, Integer maxValue = 50) {
        return dataGenerator.generateData(itemCount: itemCount, maxWeight: maxWeight, maxValue: maxValue)
    }

    void writeDataFile(List<Item> data, String path) {
        def dataMap = dataConverter.convertToMap(data)
        dataWriter.writeLines(path, dataMap)
    }

    Collection<Item> readDataFile(String path) {
        return dataReader.getLines(path)
    }

}
