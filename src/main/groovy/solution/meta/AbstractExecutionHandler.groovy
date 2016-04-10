package solution.meta
/**
 *  Created by stefangrecu on 07/04/16.
 */
abstract class AbstractExecutionHandler<S, G> {
    protected inputData
    protected processedData
    protected AbstractDataHandler<S> dataHandler
    protected AbstractResultHandler<S, G> resultHandler

    abstract void writeTestDataFile(Integer i, Integer j, Integer k, String path)

    abstract void writeResultDataFile(String path)

    abstract void processData(Map data, String path)

    abstract void createResultHandlerWith(Map data)

}