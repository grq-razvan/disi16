package io.reader.implementation.tsp

import io.converter.IDataConverter
import io.converter.implementation.tsp.DataFileConverter
import io.reader.IFileReader
import model.tsp.City
import org.apache.commons.io.FileUtils

/**
 *  Created by stefangrecu on 06/04/16.
 */
class DataFileReader implements IFileReader<City> {

    private final IDataConverter<City> cityConverter

    DataFileReader() {
        this.cityConverter = new DataFileConverter()
    }

    @Override
    Collection<City> getLines(File file) {
        List<String> lines = FileUtils.readLines(file)
        return cityConverter.convertToReadableData(lines)
    }

    @Override
    Collection<City> getLines(String path) {
        return getLines(FileUtils.getFile(path))
    }
}
