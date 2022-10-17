import args.ArgsParser
import args.OpcionParser
import args.OpcionResumen
import controllers.ParserController
import controllers.ResumenController
import exceptions.ArgsException
import exceptions.FileException
import exporting.xml.BitacoraExporter
import extensions.loggedWith
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
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
            is OpcionParser -> withBitacora(bitacoraWriter, opcion) { ParserController(opcion, logger).process() }
            is OpcionResumen -> withBitacora(bitacoraWriter, opcion) { ResumenController(opcion, logger).process() }
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


