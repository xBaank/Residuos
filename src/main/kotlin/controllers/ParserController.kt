package controllers

import args.OpcionParser
import exporting.contenedores.CsvExporterContenedores
import exporting.contenedores.JsonExporterContenedores
import exporting.contenedores.XmlExporterContenedores
import exporting.residuos.CsvExporterResiduos
import exporting.residuos.JsonExporterResiduos
import exporting.residuos.XmlExporterResiduos
import extensions.loggedWith
import importing.contenedores.CsvImporterContenedores
import importing.residuos.CsvImporterResiduos
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import mu.KLogger
import readers.CsvDirectoryReader
import writers.DirectoryWriter

class ParserController(private val opcion: OpcionParser, private val logger: KLogger) {
    
    suspend fun process() = coroutineScope {
        val residuosFileWriter = DirectoryWriter(
            opcion.directorioDestino,
            "residuos",
            JsonExporterResiduos(),
            XmlExporterResiduos(),
            CsvExporterResiduos()
        ) loggedWith logger


        val contenedoresFileWriter = DirectoryWriter(
            opcion.directorioDestino,
            "contenedores",
            JsonExporterContenedores(),
            XmlExporterContenedores(),
            CsvExporterContenedores()
        ) loggedWith logger

        val residuosFuture =
            async { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).loggedWith(logger).read() }
        val contenedoresFuture =
            async { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).loggedWith(logger).read() }

        //write async
        launch { residuosFileWriter.write(residuosFuture.await()) }
        launch { contenedoresFileWriter.write(contenedoresFuture.await()) }
    }
}