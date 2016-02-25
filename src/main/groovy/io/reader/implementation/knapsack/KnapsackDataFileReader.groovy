package io.reader.implementation.knapsack

import io.reader.IFileReader
import model.knapsack.Item
import org.apache.commons.io.FileUtils

/**
 * Created by stefangrecu on 25/02/16.
 */
class KnapsackDataFileReader implements IFileReader<Item> {
    @Override
    Collection<Item> getLines(File file) {
        Collection<Item> items = []
        List<String> data = FileUtils.readLines(file)
        data.remove(0)
        data.each {
            line ->
                def content = line.split(" ")
                items << (new Item(
                        weight: Integer.valueOf(content[1]),
                        value: Integer.valueOf(content[2])
                ))

        }
        return items
    }

    @Override
    Collection<Item> getLines(String path) {
        File file = FileUtils.getFile(path)
        return getLines(file)
    }
}
