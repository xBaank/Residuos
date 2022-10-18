import controllers.ArgsController
import exceptions.ResiduosException
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import options.OpcionParser
import options.OpcionResumen
import utils.ControllerUtils.createParseController
import utils.ControllerUtils.createResumenController
import utils.ControllerUtils.withBitacora

val logger = KotlinLogging.logger("Console")
fun main(args: Array<String>): Unit = runBlocking {
    runCatching {
        val opcion = ArgsController(args).process()

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


