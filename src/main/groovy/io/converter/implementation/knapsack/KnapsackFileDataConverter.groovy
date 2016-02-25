package io.converter.implementation.knapsack

import io.converter.IDataConverter
import model.knapsack.Item

/**
 * Created by stefangrecu on 25/02/16.
 */
class KnapsackFileDataConverter implements IDataConverter<Item> {
    @Override
    Collection<Item> convertFromMap(Map<String, Item> data) {
        Collection<Item> collection = Collections.emptyList()
        for (item in data.values()) {
            collection.add(item)
        }
        return collection
    }

    @Override
    Map<String, Item> convertToMap(Collection<Item> data) {
        Map<String, Item> map = [:]
        for (int i = 0; i < data.size(); i++) {
            map.put("${i}", data.getAt(i))
        }
        return map
    }
}
