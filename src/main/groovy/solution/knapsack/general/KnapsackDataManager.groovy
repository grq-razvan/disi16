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
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.extensivesearch.KnapsackExtensiveSearch

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

    private void addSolver(AbstractKnapsackSearcher searchSolution) {
        solvers.add(searchSolution)
    }

    private void initializeSolvers() {
        AbstractKnapsackSearcher extensiveSearch = new KnapsackExtensiveSearch()
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

    List<Knapsack> generateResult(Collection<Item> data, KnapsackSolutionType provider = KnapsackSolutionType.ExtensiveSearch, Integer randomSearchParameter = 0) {
        AbstractKnapsackSearcher searcher = getSolver(provider)
        searcher.items = data
        searcher.randomSearchParameter = randomSearchParameter
        return searcher.solve()
    }

    void writeResultFile(List<Knapsack> result, String path = FILE_RESULT_PATH + ".txt") {
        def resultMap = resultConverter.convertResultToMap(result)
        resultWriter.writeLines(path, resultMap)
    }

    private static AbstractKnapsackSearcher getSolver(KnapsackSolutionType provider) {
        switch (provider) {
            case KnapsackSolutionType.ExtensiveSearch: return new KnapsackExtensiveSearch(items: [], knapsacks: [new Knapsack(maxWeight: 100, items: [])])
            default: return new KnapsackExtensiveSearch(items: [], knapsacks: [new Knapsack(maxWeight: 100, items: [])])
        }
    }

}
