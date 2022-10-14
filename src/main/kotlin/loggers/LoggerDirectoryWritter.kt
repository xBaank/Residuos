package loggers

import mu.KLogger
import writers.DirectoryWriter

class LoggerDirectoryWritter<T>(private val writer: DirectoryWriter<T>, private val logger: KLogger) {
    suspend fun write(content: T) {
        logger.info("Writing ${writer.fileName} to ${writer.path}")
        logger.info("Formats ${writer.exporters.joinToString(separator = " ") { it.extension }}")
        writer.write(content)
    }
}