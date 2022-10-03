package writers

import parsers.residuos.Parser
import java.io.File
import java.nio.file.Files

class FileWriter<T>(private val path: String, private val mapper: Parser<T>) : Writer<T> {

    override fun write(content: Sequence<T>) = File(path)
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .outputStream()
        .use { mapper.unParse(content, it) }
}