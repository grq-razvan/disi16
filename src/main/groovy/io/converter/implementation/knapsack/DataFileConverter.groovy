package io.converter.implementation.knapsack

import generator.IFactory
import generator.implementation.knapsack.ItemFactory
import io.converter.IDataConverter
import model.knapsack.Item

/**
 *  Created by stefangrecu on 25/02/16.
 */
class DataFileConverter implements IDataConverter<Item> {

    private final IFactory<Item> itemFactory

    DataFileConverter() {
        this.itemFactory = new ItemFactory()
    }

    @Override
    Collection<Item> convertFromMap(Map<String, Item> data) {
        return data.values().collect { Item item -> item }
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
        lines.remove(0)
        return lines.collect { String data ->
            def content = data.split(" ")
            itemFactory.create(itemWeight: Integer.valueOf(content[1]), itemValue: Integer.valueOf(content[2]))
        }
    }

}