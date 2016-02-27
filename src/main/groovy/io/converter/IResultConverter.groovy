package io.converter
/**
 * Created by stefangrecu on 27/02/16.
 */
interface IResultConverter<T> {

    Map<String, T> convertResultToMap(List<T> data)

    List<String> convertResultToWritableLines(Map<String, T> map)

}