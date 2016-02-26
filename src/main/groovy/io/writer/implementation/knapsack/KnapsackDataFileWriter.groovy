package io.writer.implementation.knapsack

import io.converter.IDataConverter
import io.converter.implementation.knapsack.KnapsackFileDataConverter
import io.writer.IFileWriter
import model.knapsack.Item
import org.apache.commons.io.FileUtils

/**
 * Created by stefangrecu on 25/02/16.
 */
class KnapsackDataFileWriter implements IFileWriter<Item> {

    private final IDataConverter<Item> dataConverter

    KnapsackDataFileWriter() {
        this.dataConverter = new KnapsackFileDataConverter()
    }

    @Override
    void writeLines(File file, Map<String, Item> linesData) {
        FileUtils.writeLines(file, dataConverter.convertToWritableLines(linesData))
    }

    @Override
    void writeLines(String path, Map<String, Item> linesData) {
        writeLines(FileUtils.getFile(path), linesData)
    }
}
