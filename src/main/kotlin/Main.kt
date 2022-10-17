import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import controllers.BitacoraController
import controllers.IController
import controllers.ParserController
import controllers.ResumenController
import exceptions.ArgsException
import exceptions.ExportException
import exceptions.FileException
import exceptions.ImportException
import exporting.contenedores.CsvExporterContenedores
import exporting.contenedores.JsonExporterContenedores
import exporting.contenedores.XmlExporterContenedores
import exporting.html.HtmlDistritoExporter
import exporting.html.HtmlExporter
import exporting.residuos.CsvExporterResiduos
import exporting.residuos.JsonExporterResiduos
import exporting.residuos.XmlExporterResiduos
import exporting.xml.BitacoraExporter
import extensions.hasDistrito
import extensions.loggedWith
import importing.contenedores.CsvImporterContenedores
import importing.residuos.CsvImporterResiduos
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import readers.CsvDirectoryReader
import writers.DirectoryWriter

val logger = KotlinLogging.logger("Console")
fun main(args: Array<String>): Unit = runBlocking {
    runCatching {
        val opcion = ArgsParser(args).parse()

        val controller = when (opcion) {
            is OpcionParser -> createParseController(opcion)
            is OpcionResumen -> {
                if (opcion.hasDistrito) createResumenDistritoController(opcion)
                else createResumenController(opcion)
            }
        }

        controller
            .withBitacora(opcion)
            .process()

    }.onSuccess {
        logger.info { "Proceso finalizado correctamente" }
    }.onFailure {
        when (it) {
            //For args and file exceptions we don't log the stacktrace
            is ArgsException -> logger.error(it.message)
            is FileException -> logger.error(it.message)
            is ImportException -> logger.error(it.message)
            is ExportException -> logger.error(it.message)
            else -> logger.error(it) { it.message }
        }
    }
}

infix fun IController.withBitacora(opcion: Opcion) =
    BitacoraController(
        DirectoryWriter(
            opcion.directorioDestino,
            "bitacora",
            BitacoraExporter()
        ) loggedWith logger,
        opcion,
        this
    )

fun createParseController(opcion: OpcionParser) = ParserController(
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
    CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()) loggedWith logger,
    CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()) loggedWith logger,
)


fun createResumenController(opcion: OpcionResumen) = ResumenController(
    DirectoryWriter(
        opcion.directorioDestino,
        "residuos",
        HtmlExporter()
    ) loggedWith logger,
    CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()) loggedWith logger,
    CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()) loggedWith logger,
)

fun createResumenDistritoController(opcion: OpcionResumen) = ResumenController(
    DirectoryWriter(
        opcion.directorioDestino,
        "residuos_${opcion.distrito}",
        HtmlDistritoExporter(opcion.distrito!!)
    ) loggedWith logger,
    CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()) loggedWith logger,
    CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()) loggedWith logger,
)

