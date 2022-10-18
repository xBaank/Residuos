package writers

import exceptions.FileException
import exporting.IExporter
import java.io.File
import java.nio.file.Files

internal class FileWriter<T>(override val path: String, private val parser: IExporter<T>) : IWriter<T> {
    private val file = File(path)

    //Change context, so we don't block other threads, like ui
    override suspend fun write(content: T) = file
        .apply { if (isDirectory) throw FileException("El archivo destino no puede ser un directorio") }
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .outputStream()
        .use { parser.export(content, it) }

    override val formats: List<String>
        get() = listOf(parser.extension)
    override val name: String
        get() = file.name
}