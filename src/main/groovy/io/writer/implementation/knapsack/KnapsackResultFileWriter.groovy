package io.writer.implementation.knapsack

import io.converter.IResultConverter
import io.converter.implementation.knapsack.KnapsackResultConverter
import io.writer.IFileWriter
import model.knapsack.Knapsack
import org.apache.commons.io.FileUtils

/**
 * Created by stefangrecu on 26/02/16.
 */
class KnapsackResultFileWriter implements IFileWriter<Knapsack> {

    IResultConverter<Knapsack> resultConverter

    KnapsackResultFileWriter() {
        resultConverter = new KnapsackResultConverter()
    }

    @Override
    void writeLines(File file, Map<String, Knapsack> linesData) {
        FileUtils.writeLines(file, resultConverter.convertResultToWritableLines(linesData))
    }

    @Override
    void writeLines(String path, Map<String, Knapsack> linesData) {
        writeLines(FileUtils.getFile(path), linesData)
    }
}
