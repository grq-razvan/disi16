package solution.knapsack.general

import io.converter.IResultConverter
import io.converter.implementation.knapsack.ResultConverter
import io.writer.IFileWriter
import io.writer.implementation.knapsack.ResultWriter
import model.knapsack.Item
import model.knapsack.Knapsack
import solution.ISolver
import solution.knapsack.search.implementation.AbstractKnapsackSearcher
import solution.knapsack.search.implementation.knapsack.KnapsackSolutionType
import solution.knapsack.search.implementation.knapsack.extensivesearch.ExhaustiveSearcher
import solution.knapsack.search.implementation.knapsack.greedy.GreedySearcher
import solution.knapsack.search.implementation.knapsack.hillclimbing.AbstractHillClimbingSearcher
import solution.knapsack.search.implementation.knapsack.hillclimbing.highestascent.HighestAscentHillClimbingSearcher
import solution.knapsack.search.implementation.knapsack.hillclimbing.highestascent.HighestAscentMRMVPRSearcher
import solution.knapsack.search.implementation.knapsack.hillclimbing.highestascent.HighestAscentMRPRSearcher
import solution.knapsack.search.implementation.knapsack.hillclimbing.stochastic.StochasticHillClimbingSearcher
import solution.knapsack.search.implementation.knapsack.randomsearch.RandomSearcher

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

        }
        AbstractKnapsackSearcher extensiveSearch = new ExhaustiveSearcher(maxWeight)
        AbstractKnapsackSearcher greedySearch = new GreedySearcher(maxWeight)
        AbstractKnapsackSearcher randomSearch = new RandomSearcher(maxWeight, 0.0)
        AbstractHillClimbingSearcher stochasticHillClimb = new StochasticHillClimbingSearcher(maxWeight, 0.0)
        AbstractHillClimbingSearcher steepestAscent = new HighestAscentHillClimbingSearcher(maxWeight, 0.0)
        AbstractHillClimbingSearcher experiment1 = new HighestAscentMRPRSearcher(maxWeight, 0.0)
        AbstractHillClimbingSearcher experiment2 = new HighestAscentMRMVPRSearcher(maxWeight)
        addSolver(extensiveSearch)
        addSolver(greedySearch)
        addSolver(randomSearch)
        addSolver(stochasticHillClimb)
        addSolver(steepestAscent)
        addSolver(experiment1)
        addSolver(experiment2)
    }

    List<Knapsack> generateResult(Collection<Item> data, KnapsackSolutionType provider = KnapsackSolutionType.ExtensiveSearch, Double randomSearchParameter = 0.1) {
        AbstractKnapsackSearcher searcher = getSolver(provider)
        searcher.items = data
        searcher.adaptiveRandomQuality = randomSearchParameter
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
