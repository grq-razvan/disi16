package solution.meta

import io.converter.IResultConverter
import io.writer.IFileWriter
import solution.ISolver

/**
 *  Created by stefangrecu on 07/04/16.
 */
abstract class AbstractResultHandler<T> {
    protected IFileWriter<T> resultWriter
    protected IResultConverter<T> resultConverter
    protected List<ISolver<T>> solvers

    abstract void writeResultFile(List<T> result, String path)

}
