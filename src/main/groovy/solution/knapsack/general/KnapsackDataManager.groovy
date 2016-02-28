package solution.knapsack.general

import generator.IDataGenerator
import generator.implementation.knapsack.KnapsackRandomItemGenerator
import io.converter.IDataConverter
import io.converter.implementation.knapsack.KnapsackFileDataConverter
import io.reader.IFileReader
import io.reader.implementation.knapsack.KnapsackDataFileReader
import io.writer.IFileWriter
import io.writer.implementation.knapsack.KnapsackDataFileWriter
import model.knapsack.Item

/**
 *  Created by stefangrecu on 25/02/16.
 */
class KnapsackDataManager {

    private final IFileReader<Item> dataReader
    private final IFileWriter<Item> dataWriter
    private final IDataGenerator<Item> dataGenerator
    private final IDataConverter<Item> dataConverter

    KnapsackDataManager() {
        dataReader = new KnapsackDataFileReader()
        dataWriter = new KnapsackDataFileWriter()
        dataGenerator = new KnapsackRandomItemGenerator()
        dataConverter = new KnapsackFileDataConverter()
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
