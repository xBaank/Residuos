package parsers.xml

import extensions.toContenedor
import extensions.toResiduo
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import models.Consulta
import nl.adaptivity.xmlutil.serialization.XmlElement
import parsers.UnParser
import java.io.OutputStream
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class XmlUnparser : UnParser<Consulta> {

    @Serializable
    data class Bitacora(
        val id: String = UUID.randomUUID().toString(),
        @XmlElement(true)
        val instante: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
        @XmlElement(true)
        val opcion: TipoOpcion,
        @XmlElement(true)
        val hasExito: Boolean,
        @XmlElement(true)
        @Contextual
        val tiempoEjecucion: Duration

    )
    enum class TipoOpcion (val tipo: String) {
        PARSER("PARSER"),
        RESUMENGLOBAL("RESUMENGLOBAL"),
        RESUMENCIUDAD("RESUMENCIUDAD")
    }


    override val extension: String
        get() = ".xml"

    override fun unParse(input: Consulta, outputStream: OutputStream) {

        val contenedores = input.contenedores.toContenedor()
        val residuos = input.residuos.toResiduo()

        outputStream.bufferedWriter().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").appendLine()
    }
}