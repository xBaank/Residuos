package loggers

import mu.KLogger
import readers.CsvDirectoryReader

class LoggerCsvDirectoryReader<T>(private val reader: CsvDirectoryReader<T>, private val logger: KLogger) {
    suspend fun read(): Sequence<T> {
        logger.info("Reading ${reader.path}")
        logger.info("   Importers: ${reader.parser::class.simpleName}")
        logger.info("   Formats ${reader.parser.extension}")
        return reader.read()
    }
}