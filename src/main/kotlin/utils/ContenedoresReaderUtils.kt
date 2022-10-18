package utils

import aliases.Contenedores
import exceptions.ImportException
import importing.IImporter
import importing.contenedores.CsvImporterContenedores
import importing.contenedores.JsonImporterContenedores
import importing.contenedores.XmlImporterContenedores
import options.Opcion
import readers.CsvDirectoryReader
import readers.FileReader
import java.io.File

object ContenedoresReaderUtils {
    /**
     * Crea el reader de contenedores dependiendo de la opcion
     */
    fun createContenedoresReader(opcion: Opcion) =
        if (opcion.contenedoresFile == null) CsvDirectoryReader(
            opcion.directorioOrigen,
            CsvImporterContenedores()
        )
        else FileReader(
            "${opcion.directorioOrigen}${File.separator}${opcion.contenedoresFile}",
            getImporter(opcion.contenedoresFile!!)
        )

    /**
     * Devuelve el importer de contenedores dependiendo del formato/extension del archivo
     */
    private fun getImporter(file: String): IImporter<Contenedores> = when (val format = file.substringAfterLast('.')) {
        "csv" -> CsvImporterContenedores()
        "json" -> JsonImporterContenedores()
        "xml" -> XmlImporterContenedores()
        else -> throw ImportException("El formato $format no es válido")
    }
}