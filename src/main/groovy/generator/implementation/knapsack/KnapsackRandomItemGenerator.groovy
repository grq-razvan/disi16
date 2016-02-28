package generator.implementation.knapsack

import generator.IDataGenerator
import model.knapsack.Item
import org.apache.commons.math3.random.RandomData
import org.apache.commons.math3.random.RandomDataImpl

/**
 *  Created by stefangrecu on 25/02/16.
 */
class KnapsackRandomItemGenerator implements IDataGenerator<Item> {

    private static final Integer MAX_ITEM_WEIGHT = 10
    private static final Integer MAX_ITEM_VALUE = 50

    private final RandomData numberGenerator

    KnapsackRandomItemGenerator() {
        numberGenerator = new RandomDataImpl()
    }

    @Override
    List<Item> generateData(Map<String, Number> params) {
        List<Item> result = new ArrayList<>()
        Integer maxWeight = params.maxWeight ?: MAX_ITEM_WEIGHT
        Integer maxValue = params.maxValue ?: MAX_ITEM_VALUE
        Integer itemCount = params.itemCount
        for (item in (0..itemCount - 1)) {
            Integer currentWeight = numberGenerator.nextInt(0, maxWeight)
            Integer currentValue = numberGenerator.nextInt(0, maxValue)
            Item currentItem = new Item(
                    weight: currentWeight,
                    value: currentValue
            )
            result.add(currentItem)
        }
        return Collections.unmodifiableList(result)
    }
}
