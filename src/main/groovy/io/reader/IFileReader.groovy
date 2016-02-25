package io.reader

interface IFileReader<T> {
    Collection<T> getLines(File file)
    Collection<T> getLines(String path)
}