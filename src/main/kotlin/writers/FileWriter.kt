package writers

import core.IExporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files

internal class FileWriter<T>(val path: String, private val parser: IExporter<T>) {
    private val file = File(path)
    suspend fun write(content: T) = withContext(Dispatchers.IO) {
        file
            .apply { if (isDirectory) throw IllegalArgumentException("El archivo destino no puede ser un directorio") }
            .apply { if (exists()) Files.delete(toPath()) }
            .apply { createNewFile() }
            .outputStream()
            .use { parser.export(content, it) }
    }
}