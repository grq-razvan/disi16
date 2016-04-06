package io.converter.implementation.tsp

import generator.IFactory
import generator.implementation.tsp.CityFactory
import io.converter.IDataConverter
import model.tsp.City

/**
 *  Created by stefangrecu on 06/04/16.
 */
class DataFileConverter implements IDataConverter<City> {

    private final IFactory<City> cityFactory

    DataFileConverter() {
        cityFactory = new CityFactory()
    }

    @Override
    Collection<City> convertFromMap(Map<String, City> data) {
        return data.values().collect()
    }

    @Override
    Map<String, City> convertToMap(Collection<City> data) {
        Map<String, City> result = [:]
        (0..<data.size()).each { Integer i ->
            result.put("${i}", data.getAt(i))
        }
        return result
    }

    @Override
    List<String> convertToWritableLines(Map<String, City> data) {
        return ["${data.size()}"] + data.collect { key, value ->
            """${key} ${value}"""
        }
    }

    @Override
    Collection<City> convertToReadableData(List<String> lines) {
        lines.remove(0)
        return lines.collect { String line ->
            def content = line.split(" ")
            cityFactory.create(
                    x: Double.valueOf(content[1]),
                    y: Double.valueOf(content[2])
            )
        }
    }
}
