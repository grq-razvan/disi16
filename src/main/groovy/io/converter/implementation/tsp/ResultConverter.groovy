package io.converter.implementation.tsp

import io.converter.IResultConverter
import model.tsp.City
import model.tsp.Route

/**
 *  Created by stefangrecu on 07/04/16.
 */
class ResultConverter implements IResultConverter<Route> {
    @Override
    Map<String, Route> convertResultToMap(List<Route> data) {
        def map = [:]
        if (!data.empty) {
            (0..<data.size()).each { i ->
                map.put("${i}", data.get(i))
            }
        }
        return map
    }

    @Override
    List<String> convertResultToWritableLines(Map<String, Route> map) {
        return map.collect { key, value ->
            def builder = new StringBuilder()
                    .append("${key} : \n")
                    .append("\tExecution time (optional): ${value?.executionTime} ms")
                    .append("\tTotal cost: ${value.totalCost}\n")
                    .append("\tCities: ${value.maxNumber}\n")
            value.cities.eachWithIndex { City city, index ->
                builder.append("\t\t ${index} ${city.label}\n")
            }
            (0..100).each {
                builder.append('#')
            }
            builder.toString()
        }
    }
}
