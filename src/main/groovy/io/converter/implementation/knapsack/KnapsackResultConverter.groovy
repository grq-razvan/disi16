package io.converter.implementation.knapsack

import io.converter.IResultConverter
import model.knapsack.Knapsack

/**
 *  Created by stefangrecu on 27/02/16.
 */
class KnapsackResultConverter implements IResultConverter<Knapsack> {

    @Override
    Map<String, Knapsack> convertResultToMap(List<Knapsack> knapsacks) {
        def map = [:]
        if (!knapsacks.empty) {
            for (i in (0..knapsacks.size() - 1)) {
                map.put("${i}", knapsacks.get(i))
            }
        }
        return map
    }

    @Override
    List<String> convertResultToWritableLines(Map<String, Knapsack> map) {
        return map.collect {
            key, value ->
                def sb = new StringBuilder().append("${key} :\n")
                        .append("\tvalue: ${value.totalValue} \$\n")
                        .append("\tweight: ${value.currentWeight} kg\n")
                        .append("\tout of: ${value.maxWeight} kg\n")
                        .append("\titems:\n")
                for (item in value.items) {
                    sb.append("\t\titem: ${item}\n")
                }
                sb.append("#############END##############").toString()
        }
    }
}
