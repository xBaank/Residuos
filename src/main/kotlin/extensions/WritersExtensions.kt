package extensions

import loggers.LoggerWritter
import mu.KLogger
import writers.IFileWriter

/**
 * Agrega un logger a un writer
 */
infix fun <T> IFileWriter<T>.loggedWith(logger: KLogger?) = if (logger != null) LoggerWritter(this, logger) else this