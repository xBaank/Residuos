package controllers

import args.Opcion
import models.Bitacora
import writers.IWriter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BitacoraController(
    private val writer: IWriter<Bitacora>,
    private val opcion: Opcion,
    private val controller: IController
) : IController {
    override suspend fun process() {
        var ex: Throwable? = null
        var hasExito = true
        val start = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        val instant = Instant.now()

        runCatching {
            controller.process()
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

        throw ex ?: return
    }
}