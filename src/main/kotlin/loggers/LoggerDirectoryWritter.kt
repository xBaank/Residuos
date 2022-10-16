package loggers

import mu.KLogger
import writers.DirectoryWriter
import writers.IWriter

class LoggerDirectoryWritter<T>(private val writer: DirectoryWriter<T>, private val logger: KLogger) : IWriter<T> {
    override suspend fun write(content: T) {
        logger.info("Writing ${writer.fileName} to ${writer.path}")
        logger.info("   Formats ${writer.exporters.joinToString(separator = " ") { it.extension }}")
        logger.info("   Exporters: ${writer.exporters.map { it::class.simpleName }.joinToString(" ")}")
        writer.write(content)
    }
}