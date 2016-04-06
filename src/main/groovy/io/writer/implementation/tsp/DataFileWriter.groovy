package io.writer.implementation.tsp

import io.converter.IDataConverter
import io.converter.implementation.tsp.DataFileConverter
import io.writer.IFileWriter
import model.tsp.City
import org.apache.commons.io.FileUtils

/**
 *  Created by stefangrecu on 06/04/16.
 */
class DataFileWriter implements IFileWriter<City> {

    private final IDataConverter<City> dataConverter

    DataFileWriter() {
        this.dataConverter = new DataFileConverter()
    }

    @Override
    void writeLines(File file, Map<String, City> linesData) {
        FileUtils.writeLines(file, dataConverter.convertToWritableLines(linesData))
    }

    @Override
    void writeLines(String path, Map<String, City> linesData) {
        writeLines(FileUtils.getFile(path), linesData)
    }
}
