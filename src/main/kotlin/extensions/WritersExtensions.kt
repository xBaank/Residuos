package extensions

import loggers.LoggerWritter
import mu.KLogger
import writers.IFileWriter

infix fun <T> IFileWriter<T>.loggedWith(logger: KLogger?) = if (logger != null) LoggerWritter(this, logger) else this