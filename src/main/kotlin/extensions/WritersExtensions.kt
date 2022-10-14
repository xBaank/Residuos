package extensions

import loggers.LoggerDirectoryWritter
import mu.KLogger
import writers.DirectoryWriter

infix fun <T> DirectoryWriter<T>.loggedWith(logger: KLogger) = LoggerDirectoryWritter(this, logger)