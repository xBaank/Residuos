package dto

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.dataframe.annotations.ColumnName
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema

@Serializable
@DataSchema
data class ResiduoDto(
    @ColumnName("Año")
    val ano: String,
    @ColumnName("Mes")
    val mes: String,
    @ColumnName("Lote")
    val lote: Int,
    @ColumnName("Residuo")
    val residuo: String,
    @ColumnName("Distrito")
    val distrito: Int,
    @ColumnName("Nombre Distrito")
    val nombreDistrito: String,
    @ColumnName("Toneladas")
    val toneladas: Double,
)