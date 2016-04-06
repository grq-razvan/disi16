package io.writer.implementation.tsp

import io.converter.IResultConverter
import io.converter.implementation.tsp.ResultConverter
import io.writer.IFileWriter
import model.tsp.Route
import org.apache.commons.io.FileUtils

/**
 *  Created by stefangrecu on 07/04/16.
 */
class ResultWriter implements IFileWriter<Route> {

    IResultConverter<Route> resultConverter

    ResultWriter() {
        this.resultConverter = new ResultConverter()
    }

    @Override
    void writeLines(File file, Map<String, Route> linesData) {
        FileUtils.writeLines(file, resultConverter.convertResultToWritableLines(linesData))
    }

    @Override
    void writeLines(String path, Map<String, Route> linesData) {
        writeLines(FileUtils.getFile(path), linesData)
    }
}
