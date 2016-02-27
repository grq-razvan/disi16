package solution.knapsack.general

import generator.IDataGenerator
import generator.implementation.knapsack.KnapsackRandomItemGenerator
import io.converter.IDataConverter
import io.converter.IResultConverter
import io.converter.implementation.knapsack.KnapsackFileDataConverter
import io.converter.implementation.knapsack.KnapsackResultConverter
import io.reader.IFileReader
import io.reader.implementation.knapsack.KnapsackDataFileReader
import io.writer.IFileWriter
import io.writer.implementation.knapsack.KnapsackDataFileWriter
import io.writer.implementation.knapsack.KnapsackResultFileWriter
import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver
import solution.knapsack.search.IKnapsackSearch
import solution.knapsack.search.implementation.extensivesearch.KnapsackExtensiveSearch

/**
 * Created by stefangrecu on 25/02/16.
 */
class KnapsackDataManager {

    private static final String FILE_DATA_PATH = "src/main/resources/knapsack/data"
    private static final String FILE_RESULT_PATH = "src/main/resources/knapsack/results/data"

    private final IFileReader<Item> dataReader
    private final IFileWriter<Item> dataWriter
    private final IDataGenerator<Item> dataGenerator
    private final IDataConverter<Item> dataConverter
    private final IFileWriter<Knapsack> resultWriter
    private final IResultConverter<Knapsack> resultConverter
    private final List<ISolver<Knapsack>> solvers

    KnapsackDataManager() {
        dataReader = new KnapsackDataFileReader()
        dataWriter = new KnapsackDataFileWriter()
        dataGenerator = new KnapsackRandomItemGenerator()
        dataConverter = new KnapsackFileDataConverter()
        resultWriter = new KnapsackResultFileWriter()
        resultConverter = new KnapsackResultConverter()
        solvers = []
    }

    private void addSolver(IKnapsackSearch searchSolution) {
        solvers.add(searchSolution)
    }

    private void initializeSolvers() {
        IKnapsackSearch extensiveSearch = new KnapsackExtensiveSearch()
        addSolver(extensiveSearch)
    }

    List<Item> generateData(Integer itemCount, Integer maxWeight = 10, Integer maxValue = 50) {
        return dataGenerator.generateData(itemCount: itemCount, maxWeight: maxWeight, maxValue: maxValue)
    }

    void writeDataFile(List<Item> data, String path = FILE_DATA_PATH + data.size() + ".txt") {
        def dataMap = dataConverter.convertToMap(data)
        dataWriter.writeLines(path, dataMap)
    }

    Collection<Item> readDataFile(String path) {
        return dataReader.getLines(path)
    }

}
