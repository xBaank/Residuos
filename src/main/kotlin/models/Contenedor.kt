package models

import org.jetbrains.kotlinx.dataframe.annotations.DataSchema

@DataSchema
data class Contenedor(
    val codIntSitu: String,
    val tipoContenedor: TipoContenedor,
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

enum class TipoContenedor(val tipo: String) {
    ORGANICA("ORGANICA"),
    RESTO("RESTO"),
    VIDRIO("VIDRIO"),
    PAPEL_Y_CARTON("PAPEL-CARTON"),
    ENVASES("ENVASES"),
}