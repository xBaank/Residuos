package writers

import exceptions.FileException
import exporting.IExporter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.File.separator

class DirectoryWriter<T>(
    override val path: String,
    private val fileName: String,
    private vararg val exporters: IExporter<T>,
) : IWriter<T> {
    private val fileWriters = mutableListOf<FileWriter<T>>()

    init {
        val correctPath = File(path)
            .apply { if (isFile) throw FileException("El directorio destino no puede ser un archivo") }
            .apply { (isDirectory || mkdirs()) || throw FileException("No se pudo crear el directorio destino") }
            .path

        exporters.forEach { exporter ->
            val fileWriter = FileWriter("$correctPath$separator${createName(extension = exporter.extension)}", exporter)
            fileWriters.add(fileWriter)
        }
    }

    private fun createName(i: Int = 0, extension: String): String {
        val incrementator = if (i == 0) "" else "($i)"
        var name = fileName + incrementator + extension
        val file = File(path + separator + name)
        if (file.exists()) name = createName(i + 1, extension)
        return name
    }

    override suspend fun write(content: T) {
        coroutineScope { fileWriters.map { async { it.write(content) } }.awaitAll() }
    }

    override val formats: List<String>
        get() = exporters.map { it.extension }

    override val name: String
        get() = fileName
}