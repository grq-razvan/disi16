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
 * Created by stefangrecu on 25/02/16.
 */
class KnapsackDataManager {

    private static final String FILE_PATH = "/src/main/resources/knapsack/data"

    private final IFileReader<Item> reader
    private final IFileWriter<Item> writer
    private final IDataGenerator<Item> generator
    private final IDataConverter<Item> converter

    KnapsackDataManager() {
        this.reader = new KnapsackDataFileReader()
        this.writer = new KnapsackDataFileWriter()
        this.generator = new KnapsackRandomItemGenerator()
        this.converter = new KnapsackFileDataConverter()
    }

    void generateDataFile(Integer itemCount, Integer maxWeight = 10, Integer maxValue = 50) {
        List<Item> items = generator.generateData([itemCount: itemCount, maxWeight: maxWeight, maxValue: maxValue])
        Map<String, Item> dataMap = converter.convertToMap(items)
        writer.writeLines(new StringBuilder(FILE_PATH).append(itemCount).append(".txt").toString(), dataMap)
    }


    Collection<Item> readDataFile(String path) {
        return reader.getLines(path)
    }

    Collection<Item> readDataFile(File file) {
        return reader.getLines(file)
    }

//
//    List<Collection<Item>> readDataFolder(String path) {
//        File folder = FileUtils.getFile(path)
//        def result = [[]]
//        for(file in folder.listFiles()) {
//            if (file.isFile()) {
//                result += readDataFile(file)
//            }
//        }
//        return result
//    }


}
