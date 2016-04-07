package solution.meta
/**
 *  Created by stefangrecu on 07/04/16.
 */
abstract class AbstractExecutionHandler<S, G> {
    protected inputData
    protected processedData
    protected AbstractDataHandler<S> dataHandler
    protected AbstractResultHandler<G> resultHandler

}