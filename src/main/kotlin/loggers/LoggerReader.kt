package loggers

import mu.KLogger
import readers.IFileReader

class LoggerReader<T>(private val reader: IFileReader<Sequence<T>>, private val logger: KLogger) :
    IFileReader<Sequence<T>> {
    override val path: String
        get() = reader.path
    override val formats: List<String>
        get() = reader.formats
    override val name: String
        get() = reader.name

    override suspend fun read(): Sequence<T> {
        logger.info("Reading from ${reader.path} files ${reader.name}")
        logger.info("   Formats: ${reader.formats}")
        return reader.read()
    }
}