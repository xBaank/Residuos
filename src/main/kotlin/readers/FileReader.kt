package readers

import aliases.SequenceImporter
import exceptions.FileException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class FileReader<T>(path: String, private val parser: SequenceImporter<T>) {
    private val file = File(path)

    //Change context, so we don't block other threads, like ui
    suspend fun read(): Sequence<T> = withContext(Dispatchers.IO) {
        sequence {
            file
                .apply { if (isDirectory) throw FileException("El archivo origen no puede ser un directorio") }
                .apply { if (!exists()) throw FileException("Archivo no encontrado") }
                .inputStream()
                .use { yieldAll(parser.import(it)) }
        }
    }
}

