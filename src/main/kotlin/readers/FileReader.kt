package readers

import aliases.SequenceParser
import java.io.File
import java.io.FileNotFoundException

class FileReader<T>(path: String, private val parser: SequenceParser<T>) : Reader<T> {
    private val file = File(path)
    override fun read(): Sequence<T> = sequence {
        file
            .apply { if (isDirectory) throw IllegalArgumentException("El archivo origen no puede ser un directorio") }
            .apply { if (!exists()) throw FileNotFoundException("File not found") }
            .inputStream()
            .use { yieldAll(parser.parse(it)) }
    }
}

