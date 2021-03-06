package io.converter

/**
 *  Created by stefangrecu on 25/02/16.
 */
interface IDataConverter<T> {
    Collection<T> convertFromMap(Map<String, T> data)
    Map<String, T> convertToMap(Collection<T> data)

    List<String> convertToWritableLines(Map<String, T> data)

    Collection<T> convertToReadableData(List<String> lines)
}