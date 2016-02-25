package io.writer

/**
 * Created by stefangrecu on 25/02/16.
 */
interface IFileWriter<T> {
    void writeLines(File file, Map<String, T> linesData)
    void writeLines(String path, Map<String, T> linesData)
}