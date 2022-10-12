package dto

import kotlinx.serialization.Serializable

@Serializable
data class ContenedorDto(
    val codIntSitu: String,
    val tipoContenedor: String,
    val modelo: String,
    val descripModelo: String,
    val cantidadContenedores: Int,
    val lote: Int,
    val distrito: String,
    val barrio: String?,
    val tipoVia: String,
    val nombreVia: String,
    val numVia: Int?,
    val coordenadaX: Float?,
    val coordenadaY: Float?,
    val longitud: String,
    val latitud: String,
    val direccion: String
)