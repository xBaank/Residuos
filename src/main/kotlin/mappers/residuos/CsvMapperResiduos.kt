package mappers.residuos

import exceptions.CsvException
import extensions.*
import models.Residuo
import java.time.LocalDate
import java.time.Month
import java.util.*

/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

class CsvMapperResiduos {
    fun mapTo(input: Sequence<String>): Sequence<Residuo> = input.drop(1).map { line ->
        val (ano, mes, lote, residuo, distrito, nombreDistrito, toneladas) = line.split(';')

        val fecha = LocalDate.of(
            ano?.toIntOrNull() ?: throw CsvException("Año debe ser un numero"),
            mes?.parse() ?: throw CsvException("Mes debe estar en español"),
            1
        )

        Residuo(
            fecha = fecha,

            lote = lote?.toIntOrNull()
                ?: throw CsvException("El lote no es un número"),

            residuo = residuo?.ifBlank { throw CsvException("El residuo no puede estar vacio") }
                ?: throw CsvException("El residuo no puede ser nulo"),

            distrito = distrito?.toIntOrNull()
                ?: throw CsvException("El distrito no es un número"),

            nombreDistrito = nombreDistrito?.ifBlank { throw CsvException("El nombre del distrito no puede estar vacío") }
                ?: throw CsvException("El nombre del distrito no puede ser null"),

            toneladas = toneladas?.replace(',', '.')?.toDoubleOrNull()
                ?: throw CsvException("Las toneladas no son un número")
        )
    }

    fun mapFrom(input: Sequence<Residuo>): Sequence<String> = sequence {
        yield("Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas")
        yieldAll(input.map { residuos ->
            "${residuos.fecha.year};${residuos.fecha.month.parse()};${residuos.lote};${residuos.residuo};${residuos.distrito};${residuos.nombreDistrito};${
                residuos.toneladas.toString().replace('.', ',')
            }"
        })
    }


    private fun String.parse(): Month {
        return when (this.lowercase(Locale.getDefault())) {
            "enero" -> Month.JANUARY
            "febrero" -> Month.FEBRUARY
            "marzo" -> Month.MARCH
            "abril" -> Month.APRIL
            "mayo" -> Month.MAY
            "junio" -> Month.JUNE
            "julio" -> Month.JULY
            "agosto" -> Month.AUGUST
            "septiembre" -> Month.SEPTEMBER
            "octubre" -> Month.OCTOBER
            "noviembre" -> Month.NOVEMBER
            "diciembre" -> Month.DECEMBER
            else -> throw IllegalArgumentException("El mes no es válido")
        }
    }

    //reverse parse
    private fun Month.parse(): String {
        return when (this) {
            Month.JANUARY -> "enero"
            Month.FEBRUARY -> "febrero"
            Month.MARCH -> "marzo"
            Month.APRIL -> "abril"
            Month.MAY -> "mayo"
            Month.JUNE -> "junio"
            Month.JULY -> "julio"
            Month.AUGUST -> "agosto"
            Month.SEPTEMBER -> "septiembre"
            Month.OCTOBER -> "octubre"
            Month.NOVEMBER -> "noviembre"
            Month.DECEMBER -> "diciembre"
            else -> throw IllegalArgumentException("El mes no es válido")
        }
    }
}
