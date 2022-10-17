package controllers

import args.OpcionResumen
import exporting.html.HtmlDistritoExporter
import exporting.html.HtmlExporter
import extensions.loggedWith
import importing.contenedores.CsvImporterContenedores
import importing.residuos.CsvImporterResiduos
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import models.Consulta
import models.ConsultaDistrito
import mu.KLogger
import org.apache.commons.lang3.StringUtils
import readers.CsvDirectoryReader
import writers.DirectoryWriter

class ResumenController(private val opcion: OpcionResumen, private val logger: KLogger) {

    private val residuosReader =
        CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()) loggedWith logger
    private val contenedoresReader =
        CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()) loggedWith logger

    suspend fun process() {
        if (opcion.distrito == null) writeResumen(opcion)
        else writeResumenDistrito(opcion)
    }

    private suspend fun writeResumen(opcion: OpcionResumen) = coroutineScope {
        val residuosFuture =
            async { residuosReader.read() }
        val contenedoresFuture =
            async { contenedoresReader.read() }

        val consulta = Consulta(contenedoresFuture.await(), residuosFuture.await())

        DirectoryWriter(opcion.directorioDestino, "resumen", HtmlExporter())
            .loggedWith(logger)
            .write(consulta)
    }

    private suspend fun writeResumenDistrito(opcion: OpcionResumen) = coroutineScope {

        val residuosFuture =
            async { residuosReader.read() }
        val contenedoresFuture =
            async { contenedoresReader.read() }

        val consulta =
            ConsultaDistrito(
                contenedoresFuture.await(),
                residuosFuture.await(),
                StringUtils.stripAccents(opcion.distrito).uppercase()
            )

        DirectoryWriter(opcion.directorioDestino, "resumen${opcion.distrito}", HtmlDistritoExporter())
            .loggedWith(logger)
            .write(consulta)
    }
}