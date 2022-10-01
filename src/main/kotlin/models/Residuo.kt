package models

import kotlinx.serialization.Serializable
import serializers.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class Residuo(
    @Serializable(with = LocalDateSerializer::class)
    val fecha: LocalDate,
    val lote: Int,
    val residuo: String, //Some values are not specified in the specification
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)