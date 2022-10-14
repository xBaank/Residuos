package writers

import core.IExporter
import exceptions.FileException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files

internal class FileWriter<T>(path: String, private val parser: IExporter<T>) : IWriter<T> {
    private val file = File(path)

    //Change context, so we don't block other threads, like ui
    override suspend fun write(content: T) = withContext(Dispatchers.IO) {
        file
            .apply { if (isDirectory) throw FileException("El archivo destino no puede ser un directorio") }
            .apply { if (exists()) Files.delete(toPath()) }
            .apply { createNewFile() }
            .outputStream()
            .use { parser.export(content, it) }
    }
}