import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import exceptions.ArgsException
import exceptions.FileException
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
import importing.contenedores.CsvImporterContenedores
import importing.residuos.CsvImporterResiduos
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Consulta
import models.ConsultaDistrito
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils
import readers.CsvDirectoryReader
import utils.withBitacora
import writers.DirectoryWriter

val logger = KotlinLogging.logger("Console")
fun main(args: Array<String>): Unit = runBlocking {
    runCatching {
        val opcion = ArgsParser(args).parse()

        val bitacoraWriter = DirectoryWriter(
            opcion.directorioDestino,
            "bitacora",
            BitacoraExporter()
        ) loggedWith logger

        when (opcion) {
            is OpcionParser -> withBitacora(bitacoraWriter, opcion) { writeParser(opcion) }
            is OpcionResumen -> withBitacora(bitacoraWriter, opcion) { handleResumen(opcion) }
        }
    }.onSuccess {
        logger.info { "Proceso finalizado correctamente" }
    }.onFailure {
        when (it) {
            //For args and file exceptions we don't log the stacktrace
            is ArgsException -> logger.error(it.message)
            is FileException -> logger.error(it.message)
            else -> logger.error(it) { it.message }
        }
    }
}


suspend fun handleResumen(opcion: OpcionResumen) {
    if (opcion.distrito == null) writeResumen(opcion)
    else writeResumenDistrito(opcion)
}

suspend fun writeResumen(opcion: OpcionResumen) = coroutineScope {
    val residuosFuture =
        async { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).loggedWith(logger).read() }
    val contenedoresFuture =
        async { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).loggedWith(logger).read() }

    val consulta = Consulta(contenedoresFuture.await(), residuosFuture.await())

    DirectoryWriter(opcion.directorioDestino, "resumen", HtmlExporter())
        .loggedWith(logger)
        .write(consulta)
}

suspend fun writeResumenDistrito(opcion: OpcionResumen) = coroutineScope {

    val residuosFuture =
        async { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).loggedWith(logger).read() }
    val contenedoresFuture =
        async { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).loggedWith(logger).read() }

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

suspend fun writeParser(opcion: Opcion) = coroutineScope {
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

