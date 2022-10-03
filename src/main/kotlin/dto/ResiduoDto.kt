package dto

import kotlinx.serialization.Serializable


@Serializable
data class ResiduoDto(
    val ano: String,
    val mes: String,
    val lote: Int,
    val residuo: String, //Some values are not specified in the specification
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)