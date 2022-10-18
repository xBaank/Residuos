package utils

import aliases.Residuos
import exceptions.ImportException
import importing.IImporter
import importing.residuos.CsvImporterResiduos
import importing.residuos.JsonImporterResiduos
import importing.residuos.XmlImporterResiduos
import options.Opcion
import readers.CsvDirectoryReader
import readers.FileReader
import java.io.File

object ResiduosReadersUtils {
    /**
     * Crea el reader de residuos dependiendo de la opcion
     */
    fun createResiduosReader(opcion: Opcion) =
        //Si no se especifica el archivo, intenta autodetectar el csv
        if (opcion.residuosFile == null) CsvDirectoryReader(
            opcion.directorioOrigen,
            CsvImporterResiduos()
        )
        else FileReader(
            "${opcion.directorioOrigen}${File.separator}${opcion.residuosFile}",
            getImporter(opcion.residuosFile!!)
        )


    /**
     * Devuelve el importer de residuos dependiendo del formato/extension del archivo
     */
    private fun getImporter(file: String): IImporter<Residuos> = when (val format = file.substringAfterLast('.')) {
        "csv" -> CsvImporterResiduos()
        "json" -> JsonImporterResiduos()
        "xml" -> XmlImporterResiduos()
        else -> throw ImportException("El formato $format no es válido")
    }
}