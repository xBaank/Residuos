package loggers

import mu.KLogger
import writers.IFileWriter

class LoggerWritter<T>(private val writer: IFileWriter<T>, private val logger: KLogger) : IFileWriter<T> {
    override val path: String
        get() = writer.path
    override val formats: List<String>
        get() = writer.formats
    override val name: String
        get() = writer.name

    override suspend fun write(content: T) {
        logger.info("Writing to ${writer.path} files ${writer.name}")
        logger.info("   Formats: ${writer.formats}")
        writer.write(content)
    }
}