package readers

import exceptions.FileException
import extensions.firstLine
import formats.ICsvImporter
import java.io.File

//Lee autimaticamente el archivo csv que contenga la primera linea de csv importer
class CsvDirectoryReader<T>(override val path: String, private val parser: ICsvImporter<Sequence<T>>) :
    IReader<Sequence<T>> {

    private val fileReader: FileReader<T>

    init {
        val csvFiles = File(path)
            .apply { if (!isDirectory) throw FileException("$path no es un directorio") }
            .listFiles { _, name -> name.endsWith(parser.extension) }

        val firstLines = csvFiles?.map { file -> file to file.firstLine }
            ?: throw FileException("No se encontraron archivos .csv en el directorio $path")

        val file = firstLines.firstOrNull { it.second == parser.firstLine }
            ?: throw FileException("No se encontró el archivo con el formato correcto")

        fileReader = FileReader(file.first.path, parser)
    }

    override val formats: List<String>
        get() = listOf(parser.extension)

    override val name = fileReader.name

    override suspend fun read(): Sequence<T> = fileReader.read()
}