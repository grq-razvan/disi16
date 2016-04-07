package solution.meta

import generator.IDataGenerator
import io.converter.IDataConverter
import io.reader.IFileReader
import io.writer.IFileWriter

/**
 *  Created by stefangrecu on 07/04/16.
 */
abstract class AbstractDataHandler<T> {
    protected IFileReader<T> dataReader
    protected IFileWriter<T> dataWriter
    protected IDataGenerator<T> dataGenerator
    protected IDataConverter<T> dataConverter

    abstract void writeDataFile(List<T> data, String path)

    abstract Collection<T> readDataFile(String path)

}