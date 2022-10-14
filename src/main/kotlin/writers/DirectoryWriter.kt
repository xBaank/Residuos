package writers

import core.IExporter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.File.separator

class DirectoryWriter<T>(
    val path: String,
    val fileName: String,
    vararg val exporters: IExporter<T>,
) {
    private val fileWriters = mutableListOf<FileWriter<T>>()

    init {
        val correctPath = File(path)
            .apply { if (isFile) throw IllegalArgumentException("El directorio destino no puede ser un archivo") }
            .apply { (isDirectory || mkdirs()) || throw IllegalArgumentException("No se pudo crear el directorio destino") }
            .path

        exporters.forEach { parser ->
            val fileWriter = FileWriter("$correctPath$separator${createName(extension = parser.extension)}", parser)
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

    suspend fun write(content: T) = coroutineScope { fileWriters.map { async { it.write(content) } }.awaitAll() }
}