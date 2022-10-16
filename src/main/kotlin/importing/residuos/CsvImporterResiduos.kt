package importing.residuos

import dto.ResiduoDto
import exceptions.ImportException
import extensions.*
import formats.residuos.ICsvImporterResiduos
import java.io.InputStream

/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

class CsvImporterResiduos : ICsvImporterResiduos {


    override fun import(input: InputStream): Sequence<ResiduoDto> =
        input.bufferedReader(Charsets.UTF_8).lineSequence().filterFirstLine(firstLine).drop(1)
            .map { line ->
                val (ano, mes, lote, residuo, distrito, nombreDistrito, toneladas) = line.split(';')

                ResiduoDto(
                    ano = ano?.ifBlank { throw ImportException("") }
                        ?: throw ImportException("Año debe ser un numero"),

                    mes = mes?.ifBlank { throw ImportException("") }
                        ?: throw ImportException("Mes debe ser un numero"),

                    lote = lote?.toIntOrNull()
                        ?: throw ImportException("El lote no es un número"),

                    residuo = residuo?.ifBlank { throw ImportException("El residuo no puede estar vacio") }
                        ?: throw ImportException("El residuo no puede ser nulo"),

                    distrito = distrito?.toIntOrNull()
                        ?: throw ImportException("El distrito no es un número"),

                    nombreDistrito = nombreDistrito?.ifBlank { throw ImportException("El nombre del distrito no puede estar vacío") }
                        ?: throw ImportException("El nombre del distrito no puede ser null"),

                    toneladas = toneladas?.replace(',', '.')?.toDoubleOrNull()
                        ?: throw ImportException("Las toneladas no son un número")
                )
            }
}
