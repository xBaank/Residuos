package writers

import parsers.UnParser
import java.io.File
import java.nio.file.Files

class FileWriter<T>(path: String, private val parser: UnParser<T>) : Writer<T> {
    private val file = File(path)
    override fun write(content: T) = file
        .apply { if (isDirectory) throw IllegalArgumentException("El archivo destino no puede ser un directorio") }
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .outputStream()
        .use { parser.unParse(content, it) }
}