import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import models.Bitacora
import models.Consulta
import models.ConsultaDistrito
import org.apache.commons.lang3.StringUtils
import parsers.exporting.contenedores.CsvExporterContenedores
import parsers.exporting.contenedores.JsonExporterContenedores
import parsers.exporting.contenedores.XmlExporterContenedores
import parsers.exporting.html.HtmlDistritoExporter
import parsers.exporting.html.HtmlExporter
import parsers.exporting.residuos.CsvExporterResiduos
import parsers.exporting.residuos.JsonExporterResiduos
import parsers.exporting.residuos.XmlExporterResiduos
import parsers.exporting.xml.BitacoraExporter
import parsers.importing.contenedores.CsvImporterContenedores
import parsers.importing.residuos.CsvImporterResiduos
import readers.CsvDirectoryReader
import utils.awaitAll
import writers.DirectoryWriter
import java.util.UUID
import java.util.concurrent.CompletableFuture.supplyAsync
import kotlin.time.Duration

fun main(args: Array<String>) {

    when (val opcion = ArgsParser(args).parse()) {
        is OpcionParser -> writeParser(opcion)
        is OpcionResumen -> handleResumen(opcion)
    }
}

fun handleResumen(opcion: OpcionResumen) {
    if (opcion.distrito == null) writeResumen(opcion)
    else writeResumenDistrito(opcion)
}

fun writeResumen(opcion: OpcionResumen) {
    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).read() }

    val consulta = Consulta(contenedoresFuture.get(), residuosFuture.get())

    DirectoryWriter(opcion.directorioOrigen, "resumen", HtmlExporter()).write(consulta)
}

fun writeResumenDistrito(opcion: OpcionResumen) {
    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).read() }

    val consulta =
        ConsultaDistrito(
            contenedoresFuture.get(),
            residuosFuture.get(),
            StringUtils.stripAccents(opcion.distrito).uppercase()
        )

    DirectoryWriter(opcion.directorioOrigen, "resumen${opcion.distrito}", HtmlDistritoExporter()).write(consulta)
}

fun writeParser(opcion: Opcion) {

    val residuosFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "residuos",
        JsonExporterResiduos(),
        XmlExporterResiduos(),
        CsvExporterResiduos()
    )

    val contenedoresFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "contenedores",
        JsonExporterContenedores(),
        XmlExporterContenedores(),
        CsvExporterContenedores()
    )


    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).read() }

    //write async

    awaitAll(
        { residuosFileWriter.write(residuosFuture.get()) },
        { contenedoresFileWriter.write(contenedoresFuture.get()) }
    )

    val bitacoraFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "bitacora",
        BitacoraExporter()
    )

    val bitacora = Bitacora(UUID.randomUUID().toString(), "Resumen Global",true, Duration.ZERO)
        bitacoraFileWriter.write(bitacora)
}

