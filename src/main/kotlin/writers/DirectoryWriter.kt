package writers

import parsers.UnParser
import utils.runAsync
import java.io.File
import java.io.File.separator
import java.util.*
import java.util.function.Supplier

class DirectoryWriter<T>(
    private var path: String,
    private val fileName: String,
    vararg parsers: UnParser<T>
) : Writer<T> {
    private val fileWriters = mutableListOf<FileWriter<T>>()

    init {
        path = File(path)
            .apply { if (isFile) throw IllegalArgumentException("El directorio destino no puede ser un archivo") }
            .apply { if (!canWrite()) throw IllegalArgumentException("No se puede escribir en el directorio destino") }
            .apply { isDirectory || mkdirs() }
            .path

        parsers.forEach { parser ->
            fileWriters.add(
                FileWriter(
                    "$path$separator${fileName + UUID.randomUUID() + parser.extension}",
                    parser
                )
            )
        }
    }

    override fun write(content: T) = runAsync(*fileWriters.map { Supplier { it.write(content) } }.toTypedArray())
}