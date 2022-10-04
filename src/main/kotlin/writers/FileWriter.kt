package writers

import parsers.Parser
import java.io.File
import java.nio.file.Files

class FileWriter<T>(private val path: String, private val parser: Parser<T>) : Writer<T> {

    override fun write(content: Sequence<T>) = File(path)
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .outputStream()
        .use { parser.unParse(content, it) }
}