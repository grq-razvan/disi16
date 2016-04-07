package solution.knapsack.general

import io.converter.IResultConverter
import io.converter.implementation.knapsack.ResultConverter
import io.writer.IFileWriter
import io.writer.implementation.knapsack.ResultWriter
import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.KnapsackSolutionType
import solution.knapsack.search.implementation.exhaustivesearch.ExhaustiveSearcher
import solution.knapsack.search.implementation.greedysearch.GreedySearcher
import solution.knapsack.search.implementation.hillclimbing.AbstractHillClimbingSearcher
import solution.knapsack.search.implementation.hillclimbing.experimentsearch.ExperimentalSearcher
import solution.knapsack.search.implementation.hillclimbing.nextascentsearch.NextAscentSearcher
import solution.knapsack.search.implementation.hillclimbing.randomsearch.RandomSearcher
import solution.knapsack.search.implementation.hillclimbing.steepestascentsearch.SteepestAscentSearcher
import solution.knapsack.search.implementation.randomsearch.RandomSearcher as RandomKnapsackSearcher

/**
 *  Created by stefangrecu on 27/02/16.
 */
class ResultHandler {
    private final IFileWriter<Knapsack> resultWriter
    private final IResultConverter<Knapsack> resultConverter
    private final List<ISolver<Knapsack>> solvers

    ResultHandler(Integer maxWeight) {
        resultWriter = new ResultWriter()
        resultConverter = new ResultConverter()
        solvers = []
        initializeSolvers(maxWeight)
    }

    private void addSolver(AbstractKnapsackSearcher searchSolution) {
        solvers.add(searchSolution)
    }

    private void initializeSolvers(Integer maxWeight) {
        if (solvers.empty) {
            addSolver new ExhaustiveSearcher(maxWeight)
            addSolver new GreedySearcher(maxWeight)
            addSolver new RandomKnapsackSearcher(maxWeight, 0.0)
            addSolver new RandomSearcher(maxWeight, 0.0)
            addSolver new SteepestAscentSearcher(maxWeight, 0.0)
            addSolver new NextAscentSearcher(maxWeight, 0.0)
            addSolver new ExperimentalSearcher(maxWeight, 0.0)
        }

    }

    List<Knapsack> generateResult(Collection<Item> data, KnapsackSolutionType provider = KnapsackSolutionType.Exhaustive, Double randomSearchParameter = 0.1, Integer numberOfRegions = 1, Integer maxDegree = 1) {
        AbstractKnapsackSearcher searcher = getSolver(provider)
        searcher.items = data
        searcher.adaptiveRandomQuality = randomSearchParameter
        if (searcher instanceof AbstractHillClimbingSearcher) {
            def hcSearcher = searcher as AbstractHillClimbingSearcher
            hcSearcher.runtimeParams.putIfAbsent('regions', numberOfRegions)
            hcSearcher.runtimeParams.putIfAbsent('maxDegree', maxDegree)
            return hcSearcher.solve()
        }
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
                if (possibleResult instanceof AbstractHillClimbingSearcher) {
                    def hillClimbPossibleSolution = possibleResult as AbstractHillClimbingSearcher
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
