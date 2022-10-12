package models

import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import java.time.LocalDate

@DataSchema
data class Residuo(
    val fecha: LocalDate,
    val lote: Int,
    val residuo: String, //Some values are not specified in the specification
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)