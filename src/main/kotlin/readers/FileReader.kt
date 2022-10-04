package readers

import parsers.Parser
import java.io.File
import java.io.FileNotFoundException

class FileReader<T>(private val path: String, private val parser: Parser<T>) : Reader<T> {

    override fun read(): Sequence<T> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException("File not found") }
            .inputStream()
            .use { yieldAll(parser.parse(it)) }
    }
}

