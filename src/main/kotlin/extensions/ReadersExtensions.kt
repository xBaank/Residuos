package extensions

import loggers.LoggerCsvDirectoryReader
import mu.KLogger
import readers.CsvDirectoryReader

infix fun <T> CsvDirectoryReader<T>.loggedWith(logger: KLogger) = LoggerCsvDirectoryReader(this, logger)