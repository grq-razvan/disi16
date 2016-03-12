package generator.implementation.knapsack

import generator.IFactory
import model.knapsack.Item

/**
 *  Created by stefangrecu on 12/03/16.
 */
class ItemFactory implements IFactory<Item> {
    @Override
    Item create(Map<String, Object> params) {
        Integer weight = params.itemWeight as Integer
        Integer value = params.itemValue as Integer
        return new Item(weight: weight, value: value)
    }
}
