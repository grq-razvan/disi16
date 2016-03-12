package io.reader.implementation.knapsack

import io.converter.IDataConverter
import io.converter.implementation.knapsack.DataFileConverter
import io.reader.IFileReader
import model.knapsack.Item
import org.apache.commons.io.FileUtils

/**
 *  Created by stefangrecu on 25/02/16.
 */
class DataFileReader implements IFileReader<Item> {

    private final IDataConverter<Item> dataConverter

    DataFileReader() {
        this.dataConverter = new DataFileConverter()
    }

    @Override
    Collection<Item> getLines(File file) {
        List<String> data = FileUtils.readLines(file)
        return dataConverter.convertToReadableData(data)
    }

    @Override
    Collection<Item> getLines(String path) {
        return getLines(FileUtils.getFile(path))
    }
}
