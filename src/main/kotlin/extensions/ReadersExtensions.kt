package extensions

import loggers.LoggerReader
import mu.KLogger
import readers.IFileReader

infix fun <T> IFileReader<Sequence<T>>.loggedWith(logger: KLogger?) =
    if (logger != null) LoggerReader(this, logger) else this