package loggers

import mu.KLogger
import readers.CsvDirectoryReader
import readers.IReader

class LoggerCsvDirectoryReader<T>(private val reader: CsvDirectoryReader<T>, private val logger: KLogger) :
    IReader<Sequence<T>> {
    override suspend fun read(): Sequence<T> {
        logger.info("Reading ${reader.path}")
        logger.info("   Importers: ${reader.parser::class.simpleName}")
        logger.info("   Formats ${reader.parser.extension}")
        return reader.read()
    }
}