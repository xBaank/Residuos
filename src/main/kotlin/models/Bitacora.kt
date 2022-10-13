package models

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import java.util.*
import kotlin.time.Duration

@Serializable
data class Bitacora(
    val id: String = UUID.randomUUID().toString(),
    @XmlElement(true)
    val opcion: String,
    @XmlElement(true)
    val hasExito: Boolean,
    @XmlElement(true)
    val tiempoEjecucion: Duration
)
