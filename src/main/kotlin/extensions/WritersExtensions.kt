package extensions

import loggers.LoggerWritter
import mu.KLogger
import writers.IWriter

infix fun <T> IWriter<T>.loggedWith(logger: KLogger?) = if (logger != null) LoggerWritter(this, logger) else this