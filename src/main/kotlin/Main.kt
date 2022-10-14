import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import core.exporting.contenedores.CsvExporterContenedores
import core.exporting.contenedores.JsonExporterContenedores
import core.exporting.contenedores.XmlExporterContenedores
import core.exporting.html.HtmlDistritoExporter
import core.exporting.html.HtmlExporter
import core.exporting.residuos.CsvExporterResiduos
import core.exporting.residuos.JsonExporterResiduos
import core.exporting.residuos.XmlExporterResiduos
import core.exporting.xml.BitacoraExporter
import core.importing.contenedores.CsvImporterContenedores
import core.importing.residuos.CsvImporterResiduos
import extensions.loggedWith
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Bitacora
import models.Consulta
import models.ConsultaDistrito
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils
import readers.CsvDirectoryReader
import writers.DirectoryWriter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

val logger = KotlinLogging.logger("Console")
fun main(args: Array<String>) = runBlocking {
    when (val opcion = ArgsParser(args).parse()) {
        is OpcionParser -> withBitacora(opcion) { writeParser(opcion) }
        is OpcionResumen -> withBitacora(opcion) { handleResumen(opcion) }
    }
}

suspend fun withBitacora(opcion: Opcion, process: suspend () -> Unit) {
    var ex: Throwable? = null
    var hasExito = true
    val start = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    val instant = Instant.now()

    runCatching {
        process()
    }.onSuccess {
        hasExito = true
    }.onFailure {
        hasExito = false
        ex = it
    }

    val writerBitacora = DirectoryWriter(opcion.directorioDestino, "bitacora", BitacoraExporter()) loggedWith logger
    writerBitacora.write(
        Bitacora(
            UUID.randomUUID().toString(),
            start,
            opcion.toString(),
            hasExito,
            Duration.between(Instant.now(), instant).toString()
        )
    )

    throw ex ?: return
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

