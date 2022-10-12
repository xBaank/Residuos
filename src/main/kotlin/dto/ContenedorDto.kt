package dto

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.dataframe.annotations.ColumnName
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema

@Serializable
@DataSchema
data class ContenedorDto(
    @ColumnName("Código Interno del Situad")
    val codIntSitu: String,
    @ColumnName("Tipo Contenedor")
    val tipoContenedor: String,
    @ColumnName("Modelo")
    val modelo: String,
    @ColumnName("Descripcion Modelo")
    val descripModelo: String,
    @ColumnName("Cantidad")
    val cantidadContenedores: Int,
    @ColumnName("Lote")
    val lote: Int,
    @ColumnName("Distrito")
    val distrito: String,
    @ColumnName("Barrio")
    val barrio: String?,
    @ColumnName("Tipo Vía")
    val tipoVia: String,
    @ColumnName("Nombre")
    val nombreVia: String,
    @ColumnName("Número")
    val numVia: Int?,
    @ColumnName("COORDENADA X")
    val coordenadaX: Float?,
    @ColumnName("COORDENADA Y")
    val coordenadaY: Float?,
    @ColumnName("LONGITUD")
    val longitud: String,
    @ColumnName("LATITUD")
    val latitud: String,
    @ColumnName("DIRECCION")
    val direccion: String
)