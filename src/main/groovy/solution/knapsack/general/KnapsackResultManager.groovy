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
import solution.knapsack.search.implementation.knapsack.greedy.KnapsackGreedySearch
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractKnapsackHillClimbingSearcher
import solution.knapsack.search.implementation.knapsack.hillclimbing.stochastic.KnapsackStochasticHillClimbingSearcher
import solution.knapsack.search.implementation.knapsack.randomsearch.KnapsackRandomSearch

/**
 *  Created by stefangrecu on 27/02/16.
 */
class KnapsackResultManager {
    private final IFileWriter<Knapsack> resultWriter
    private final IResultConverter<Knapsack> resultConverter
    private final List<ISolver<Knapsack>> solvers

    KnapsackResultManager(Integer maxWeight, Double epsilon) {
        resultWriter = new KnapsackResultFileWriter()
        resultConverter = new KnapsackResultConverter()
        solvers = []
        initializeSolvers(maxWeight, epsilon)
    }

    private void addSolver(AbstractKnapsackSearcher searchSolution) {
        solvers.add(searchSolution)
    }

    private void initializeSolvers(Integer maxWeight, Double searchEpsilon) {
        AbstractKnapsackSearcher extensiveSearch = new KnapsackExtensiveSearch(maxWeight)
        AbstractKnapsackSearcher greedySearch = new KnapsackGreedySearch(maxWeight)
        AbstractKnapsackSearcher randomSearch = new KnapsackRandomSearch(maxWeight, searchEpsilon)
        AbstractKnapsackHillClimbingSearcher stochasticHillClimb = new KnapsackStochasticHillClimbingSearcher(maxWeight, searchEpsilon)
        addSolver(extensiveSearch)
        addSolver(greedySearch)
        addSolver(randomSearch)
        addSolver(stochasticHillClimb)
    }

    List<Knapsack> generateResult(Collection<Item> data, KnapsackSolutionType provider = KnapsackSolutionType.ExtensiveSearch, Double randomSearchParameter = 0.1) {
        AbstractKnapsackSearcher searcher = getSolver(provider)
        searcher.items = data
        searcher.randomSearchParameter = randomSearchParameter
        return searcher.solve()
    }

    void writeResultFile(List<Knapsack> result, String path) {
        def resultMap = resultConverter.convertResultToMap(result)
        resultWriter.writeLines(path, resultMap)
    }

    private AbstractKnapsackSearcher getSolver(KnapsackSolutionType type) {
        for (solver in solvers) {
            if (solver instanceof AbstractKnapsackSearcher) {
                def possibleResult = solver as AbstractKnapsackSearcher
                if (possibleResult instanceof AbstractKnapsackHillClimbingSearcher) {
                    def hillClimbPossibleSolution = possibleResult as AbstractKnapsackHillClimbingSearcher
                    if (hillClimbPossibleSolution.type == type) {
                        return hillClimbPossibleSolution
                    }
                }
                if (possibleResult.type == type) {
                    return possibleResult
                }
            }
        }
        return null
    }
}
