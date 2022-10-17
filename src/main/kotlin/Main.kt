import args.ArgsParser
import args.OpcionParser
import args.OpcionResumen
import exceptions.ResiduosException
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import utils.ControllerUtils.createParseController
import utils.ControllerUtils.createResumenController
import utils.ControllerUtils.withBitacora

val logger = KotlinLogging.logger("Console")
fun main(args: Array<String>): Unit = runBlocking {
    runCatching {
        val opcion = ArgsParser(args).parse()

        val controller = when (opcion) {
            is OpcionParser -> createParseController(opcion, logger)
            is OpcionResumen -> createResumenController(opcion, logger)
        }

        controller
            .withBitacora(opcion, logger)
            .process()

    }.onSuccess {
        logger.info { "Proceso finalizado correctamente" }
    }.onFailure {
        when (it) {
            is ResiduosException -> logger.error(it.message)
            else -> logger.error(it) { it.message }
        }
    }
}


