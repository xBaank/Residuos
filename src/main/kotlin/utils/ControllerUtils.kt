package utils

import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import controllers.BitacoraController
import controllers.IController
import controllers.ParserController
import controllers.ResumenController
import exporting.contenedores.CsvExporterContenedores
import exporting.contenedores.JsonExporterContenedores
import exporting.contenedores.XmlExporterContenedores
import exporting.html.HtmlDistritoExporter
import exporting.html.HtmlExporter
import exporting.residuos.CsvExporterResiduos
import exporting.residuos.JsonExporterResiduos
import exporting.residuos.XmlExporterResiduos
import exporting.xml.BitacoraExporter
import extensions.loggedWith
import mu.KLogger
import utils.ContenedoresReaderUtils.createContenedoresReader
import utils.ResiduosReadersUtils.createResiduosReader
import writers.DirectoryWriter

object ControllerUtils {
    fun createResumenController(opcion: OpcionResumen, logger: KLogger? = null) =
        if (opcion.distrito == null) createResumenGlobalController(opcion, logger)
        else createResumenDistritoController(opcion, logger)

    private fun createResumenDistritoController(opcion: OpcionResumen, logger: KLogger? = null) = ResumenController(
        DirectoryWriter(
            opcion.directorioDestino,
            "residuos_${opcion.distrito}",
            HtmlDistritoExporter(opcion.distrito!!)
        ) loggedWith logger,
        createResiduosReader(opcion) loggedWith logger,
        createContenedoresReader(opcion) loggedWith logger,
    )

    private fun createResumenGlobalController(opcion: OpcionResumen, logger: KLogger? = null) = ResumenController(
        DirectoryWriter(
            opcion.directorioDestino,
            "residuos",
            HtmlExporter()
        ) loggedWith logger,
        createResiduosReader(opcion) loggedWith logger,
        createContenedoresReader(opcion) loggedWith logger,
    )

    fun createParseController(opcion: OpcionParser, logger: KLogger? = null) = ParserController(
        DirectoryWriter(
            opcion.directorioDestino,
            "residuos",
            JsonExporterResiduos(),
            CsvExporterResiduos(),
            XmlExporterResiduos()
        ) loggedWith logger,
        DirectoryWriter(
            opcion.directorioDestino,
            "contenedores",
            CsvExporterContenedores(),
            JsonExporterContenedores(),
            XmlExporterContenedores()
        ) loggedWith logger,
        createResiduosReader(opcion) loggedWith logger,
        createContenedoresReader(opcion) loggedWith logger,
    )

    fun IController.withBitacora(opcion: Opcion, logger: KLogger) =
        BitacoraController(
            DirectoryWriter(
                opcion.directorioDestino,
                "bitacora",
                BitacoraExporter()
            ) loggedWith logger,
            opcion,
            this
        )
}