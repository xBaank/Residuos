package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import parsers.xml.TipoOpcion
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration

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
