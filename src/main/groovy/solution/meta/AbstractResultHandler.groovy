package solution.meta

import io.converter.IResultConverter
import io.writer.IFileWriter
import solution.ISolver

/**
 *  Created by stefangrecu on 07/04/16.
 */
abstract class AbstractResultHandler<S, G> {
    protected IFileWriter<G> resultWriter
    protected IResultConverter<G> resultConverter
    protected List<ISolver<G>> solvers

    abstract void writeResultFile(List<G> result, String path)

    abstract List<G> generateResult(Collection<S> entities, Map data)
}
