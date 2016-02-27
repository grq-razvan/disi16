package solution.knapsack.general

import io.converter.IResultConverter
import io.converter.implementation.knapsack.KnapsackResultConverter
import io.writer.IFileWriter
import io.writer.implementation.knapsack.KnapsackResultFileWriter
import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.extensivesearch.KnapsackExtensiveSearch

/**
 * Created by stefangrecu on 27/02/16.
 */
class KnapsackResultManager {
    private static final String FILE_RESULT_PATH = "src/main/resources/knapsack/results/data"

    private final IFileWriter<Knapsack> resultWriter
    private final IResultConverter<Knapsack> resultConverter
    private final List<ISolver<Knapsack>> solvers

    KnapsackResultManager() {
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

    private AbstractKnapsackSearcher getSolver(KnapsackSolutionType type) {
        if (solvers.empty) {
            initializeSolvers()
        }
        for (solver in solvers) {
            if (solver instanceof AbstractKnapsackSearcher) {
                def searcher = solver as AbstractKnapsackSearcher
                if (searcher.type == type) {
                    return searcher
                }
            }
            return null
        }
    }
}
