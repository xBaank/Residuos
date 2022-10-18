package readers

import exceptions.FileException
import importing.IImporter
import java.io.File

class FileReader<T>(override val path: String, private val parser: IImporter<Sequence<T>>) : IReader<Sequence<T>> {
    private val file = File(path)
    override val formats: List<String>
        get() = listOf(parser.extension)
    override val name: String
        get() = file.name

    //Change context, so we don't block other threads, like ui
    override suspend fun read(): Sequence<T> = sequence {
        file
            .apply { if (isDirectory) throw FileException("El archivo origen no puede ser un directorio") }
            .apply { if (!exists()) throw FileException("Archivo $name no encontrado") }
            .inputStream()
            .use { yieldAll(parser.import(it)) }
    }
}

