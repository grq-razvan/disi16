package generator

/**
 *  Created by stefangrecu on 25/02/16.
 */
interface IDataGenerator<T> {

    List<T> generateData(Map<String, Number> params)

}