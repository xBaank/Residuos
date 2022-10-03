package readers

import parsers.residuos.Parser
import java.io.File
import java.io.FileNotFoundException

class FileReader<T>(private val path: String, private val mapper: Parser<T>) : Reader<T> {

    override fun read(): Sequence<T> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .inputStream()
            .use { yieldAll(mapper.parse(it)) }
    }
}

