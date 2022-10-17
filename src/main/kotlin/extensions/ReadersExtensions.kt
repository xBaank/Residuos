package extensions

import loggers.LoggerReader
import mu.KLogger
import readers.IReader

infix fun <T> IReader<Sequence<T>>.loggedWith(logger: KLogger?) =
    if (logger != null) LoggerReader(this, logger) else this