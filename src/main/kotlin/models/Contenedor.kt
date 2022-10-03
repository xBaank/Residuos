package models

import kotlinx.serialization.Serializable

@Serializable
data class Contenedor (
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
        val coordenadaX: Long,
        val coordenadaY: Long,
        val longitud: Int,
        val latitud: Int,
        val direccion: String
        )