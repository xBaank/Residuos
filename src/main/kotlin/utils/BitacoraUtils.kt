package utils

import args.Opcion
import models.Bitacora
import writers.IWriter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

suspend fun withBitacora(writer: IWriter<Bitacora>, opcion: Opcion, process: suspend () -> Unit) {
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