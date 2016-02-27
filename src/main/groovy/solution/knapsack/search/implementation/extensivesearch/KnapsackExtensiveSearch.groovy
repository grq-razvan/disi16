package solution.knapsack.search.implementation.extensivesearch

import model.knapsack.Item
import model.knapsack.Knapsack
import org.apache.commons.math3.util.ArithmeticUtils
import solution.knapsack.general.KnapsackDataManager
import solution.knapsack.search.IKnapsackSearch

/**
 * Created by stefangrecu on 26/02/16.
 */
class KnapsackExtensiveSearch implements IKnapsackSearch {

    private static final String PLACEHOLDER_PATH = 'src/main/resources/knapsack/data10.txt'

    private KnapsackDataManager knapsackDataManager
    private Knapsack knapsack
    private List<Item> items

    KnapsackExtensiveSearch() {
        this.knapsackDataManager = new KnapsackDataManager()
        this.knapsack = new Knapsack(maxWeight: 100, items: [])
        this.items = []
    }

    public void getData(String path = null) {
        items = knapsackDataManager.readDataFile(path ?: PLACEHOLDER_PATH)
    }

    private static List<String> generateBinaryStrings(Integer itemCount) {
        def result = []
        for (counter in (1..ArithmeticUtils.pow(2, itemCount) - 1)) {
            String binaryRepresentation = Integer.toBinaryString(counter)
            while (binaryRepresentation.length() < itemCount) {
                binaryRepresentation = '0' + binaryRepresentation
            }
            result.add(binaryRepresentation)
        }
        return result
    }

    public void solveInMemory() {
        if (items.empty) {
            getData()
        }
        ArrayList<Knapsack> knapsacks = computeSolution()
        knapsack = knapsacks.sort {
            knapsack1, knapsack2 -> return knapsack2.totalValue <=> knapsack1.totalValue
        }.first()

    }

    public List<Knapsack> solve() {
        if (items.empty) {
            getData()
        }
        ArrayList<Knapsack> knapsacks = computeSolution()
        return [knapsacks.sort {
            knapsack1, knapsack2 -> knapsack2.totalValue <=> knapsack1.totalValue
        }.first()]
    }

    private List<Knapsack> computeSolution() {
        List<String> possibleMatches = generateBinaryStrings(items.size())
        List<Knapsack> knapsacks = new ArrayList<>()
        possibleMatches.each {
            knapsacks.add(new Knapsack(maxWeight: knapsack.maxWeight, items: []))
        }

        possibleMatches.eachWithIndex { match, index ->
            Knapsack currentKnapsack = knapsacks.get(index)
            match.eachWithIndex { String entry, int i ->
                if (entry == '1') {
                    Item currentItem = items.get(i)
                    currentKnapsack.addItem(currentItem)
                }
            }
        }

        return knapsacks
    }


}
