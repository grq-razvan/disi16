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

    @Override
    List<String> convertToWritableLines(Map<String, Item> data) {
        return ["${data.size()}"] + data.collect { key, value -> """${key} ${value}""" }
    }

    @Override
    Collection<Item> convertToReadableData(List<String> lines) {
        Collection<Item> result = []

        //remove first elements, which is usually size
        lines.remove(0)

        lines.each {
            line ->
                //three space based input: index, weight, value
                def content = line.split(" ")
                result << new Item(weight: Integer.valueOf(content[1]), value: Integer.valueOf(content[2]))
        }
        return result
    }

}