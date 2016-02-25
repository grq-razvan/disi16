package solution.knapsack.extensivesearch

import model.knapsack.Item
import model.knapsack.Knapsack
import org.apache.commons.math3.util.ArithmeticUtils
import solution.knapsack.general.KnapsackDataManager

/**
 * Created by stefangrecu on 26/02/16.
 */
class KnapsackExtensiveSearch {

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

    private List<String> generateBinaryStrings(Integer itemCount) {
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


}
