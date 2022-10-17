package utils

import aliases.Residuos
import args.Opcion
import exceptions.ImportException
import importing.IImporter
import importing.residuos.CsvImporterResiduos
import importing.residuos.JsonImporterResiduos
import importing.residuos.XmlImporterResiduos
import readers.CsvDirectoryReader
import readers.FileReader
import java.io.File

object ResiduosReadersUtils {
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

    private fun getImporter(file: String): IImporter<Residuos> = when (val format = file.substringAfterLast('.')) {
        "csv" -> CsvImporterResiduos()
        "json" -> JsonImporterResiduos()
        "xml" -> XmlImporterResiduos()
        else -> throw ImportException("El formato $format no es válido")
    }
}