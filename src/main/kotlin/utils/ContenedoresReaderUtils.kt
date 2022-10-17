package utils

import aliases.Contenedores
import args.Opcion
import exceptions.ImportException
import importing.IImporter
import importing.contenedores.CsvImporterContenedores
import importing.contenedores.JsonImporterContenedores
import importing.contenedores.XmlImporterContenedores
import readers.CsvDirectoryReader
import readers.FileReader
import java.io.File

object ContenedoresReaderUtils {
    fun createContenedoresReader(opcion: Opcion) =
        if (opcion.contenedoresFile == null) CsvDirectoryReader(
            opcion.directorioOrigen,
            CsvImporterContenedores()
        )
        else FileReader(
            "${opcion.directorioOrigen}${File.separator}${opcion.contenedoresFile}",
            getImporter(opcion.contenedoresFile!!)
        )

    private fun getImporter(file: String): IImporter<Contenedores> = when (val format = file.substringAfterLast('.')) {
        "csv" -> CsvImporterContenedores()
        "json" -> JsonImporterContenedores()
        "xml" -> XmlImporterContenedores()
        else -> throw ImportException("El formato $format no es válido")
    }
}