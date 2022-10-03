package parsers.residuos

import dto.ResiduoDto
import exceptions.CsvException
import extensions.*
import java.io.InputStream
import java.io.OutputStream
import java.time.Month
import java.util.*

/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

class CsvParserResiduos : Parser<ResiduoDto> {
    override fun parse(input: InputStream): Sequence<ResiduoDto> =
        input.bufferedReader().lineSequence().drop(1).map { line ->
            val (ano, mes, lote, residuo, distrito, nombreDistrito, toneladas) = line.split(';')

            ResiduoDto(
                ano = ano?.ifBlank { throw CsvException("") }
                    ?: throw CsvException("Año debe ser un numero"),

                mes = mes?.ifBlank { throw CsvException("") }
                    ?: throw CsvException("Mes debe ser un numero"),

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

    override fun unParse(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        outputStream.bufferedWriter().run {
            appendLine("Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas")
            input.map { residuo ->
                "${residuo.ano};${residuo.mes};${residuo.lote};${residuo.residuo};${residuo.distrito};${residuo.nombreDistrito};${residuo.toneladas}"
            }.forEach { appendLine(it) }

            flush()
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
            else -> throw CsvException("El mes no es válido")
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
            else -> throw CsvException("El mes no es válido")
        }
    }
}
