package readers

import aliases.SequenceImporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

class FileReader<T>(val path: String, private val parser: SequenceImporter<T>) {
    private val file = File(path)
    suspend fun read(): Sequence<T> = withContext(Dispatchers.IO) {
        sequence {
            file
                .apply { if (isDirectory) throw IllegalArgumentException("El archivo origen no puede ser un directorio") }
                .apply { if (!exists()) throw FileNotFoundException("File not found") }
                .inputStream()
                .use { yieldAll(parser.import(it)) }
        }
    }
}

