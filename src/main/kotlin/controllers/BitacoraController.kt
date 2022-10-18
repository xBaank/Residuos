package controllers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.Bitacora
import options.Opcion
import writers.IFileWriter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Controlador para la bitácora, compuesto por el un controlador subyacente que es el que hara el proceso de escritura
 */
class BitacoraController<T>(
    private val writer: IFileWriter<Bitacora>,
    private val opcion: Opcion,
    private val controller: IController<T>,
) : IController<T> {
    override suspend fun process(): T = withContext(Dispatchers.IO) {
        var ex: Throwable? = null
        var hasExito = true
        val start = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        val instant = Instant.now()

        var result: T? = null

        runCatching {
            result = controller.process()
        }.onSuccess {
            hasExito = true
        }.onFailure {
            hasExito = false
            ex = it
        }

        writer.write(
            Bitacora(
                UUID.randomUUID().toString(),
                start,
                opcion.toString(),
                hasExito,
                Duration.between(Instant.now(), instant).toString()
            )
        )

        //Si no throwamos la excepcion, el resultado no puede ser nulo
        throw ex ?: return@withContext result!!
    }
}