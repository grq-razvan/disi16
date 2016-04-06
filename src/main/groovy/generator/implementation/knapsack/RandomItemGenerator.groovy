package generator.implementation.knapsack

import generator.IDataGenerator
import generator.IFactory
import model.knapsack.Item
import org.apache.commons.math3.random.RandomDataGenerator

/**
 *  Created by stefangrecu on 25/02/16.
 */
class RandomItemGenerator implements IDataGenerator<Item> {

    private static final Integer MAX_ITEM_WEIGHT = 10
    private static final Integer MAX_ITEM_VALUE = 50

    private final RandomDataGenerator numberGenerator
    private final IFactory<Item> itemFactory

    RandomItemGenerator() {
        numberGenerator = new RandomDataGenerator()
        itemFactory = new ItemFactory()
    }

    @Override
    List<Item> generateData(Map<String, Number> params) {
        Integer maxWeight = params.maxWeight ?: MAX_ITEM_WEIGHT
        Integer maxValue = params.maxValue ?: MAX_ITEM_VALUE
        Integer itemCount = params.itemCount
        return Collections.unmodifiableList(
                (0..<itemCount).collect {
                    Integer currentWeight = numberGenerator.nextInt(0, maxWeight)
                    Integer currentValue = numberGenerator.nextInt(0, maxValue)
                    itemFactory.create(
                            [
                                    itemWeight: currentWeight,
                                    itemValue : currentValue
                            ]
                    )
                }
        )

    }
}
