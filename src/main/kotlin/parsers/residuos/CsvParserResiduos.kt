package parsers.residuos

import aliases.Residuos
import dto.ResiduoDto
import exceptions.CsvException
import extensions.*
import parsers.formats.CsvParser
import java.io.InputStream
import java.io.OutputStream

/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

class CsvParserResiduos : CsvParser<Residuos> {
    override val firstLine: String
        get() = "Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas"


    override fun parse(input: InputStream): Sequence<ResiduoDto> =
        input.bufferedReader(Charsets.UTF_8).lineSequence().filterFirstLine(firstLine).drop(1)
            .map { line ->
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
                "${residuo.ano};${residuo.mes};${residuo.lote};${residuo.residuo};${residuo.distrito};${residuo.nombreDistrito};${
                    residuo.toneladas.toString().replace('.', ',')
                }"
            }.forEach { appendLine(it) }

            flush()
        }

}
