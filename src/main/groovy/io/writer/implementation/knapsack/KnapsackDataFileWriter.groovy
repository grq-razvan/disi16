package io.writer.implementation.knapsack

import io.writer.IFileWriter
import model.knapsack.Item
import org.apache.commons.io.FileUtils

/**
 * Created by stefangrecu on 25/02/16.
 */
class KnapsackDataFileWriter implements IFileWriter<Item> {

    @Override
    void writeLines(File file, Map<String, Item> linesData) {
        def lines = ["""${linesData.size()}"""] + linesData.collect { k, v -> """${k} ${v}""" }
        FileUtils.writeLines(file, lines)
    }

    @Override
    void writeLines(String path, Map<String, Item> linesData) {
        def lines = ["""${linesData.size()}"""] + linesData.collect { k, v -> """${k} ${v}""" }
        File file = FileUtils.getFile(path)
        FileUtils.writeLines(file, lines)
    }
}
