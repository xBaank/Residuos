package importing.contenedores

import dto.ContenedorDto
import exceptions.ImportException
import extensions.*
import formats.contenedores.ICsvImporterContenedores
import java.io.InputStream


/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */


class CsvImporterContenedores : ICsvImporterContenedores {
    override fun import(input: InputStream): Sequence<ContenedorDto> =
        input.bufferedReader().lineSequence().filterFirstLine(firstLine).drop(1).map { line ->

            val (codIntSitu, tipoContenedor, modelo, descripModelo, cantidadContenedores, lote, distrito, barrio, tipoVia, nombreVia, numVia, coordenadaX, coordenadaY, longitud, latitud, direccion) = line.split(
                ';'
            )

            ContenedorDto(

                codIntSitu = codIntSitu?.ifBlank { throw ImportException("El codigo no puede estar vacio") }
                    ?: throw ImportException("El codigo no puede ser nulo"),

                tipoContenedor = tipoContenedor?.ifBlank { throw ImportException("El tipo del contenedor nu puede estar vacío") }
                    ?: throw ImportException("El tipo no puede ser nulo"),

                modelo = modelo?.ifBlank { throw ImportException("El modelo no puede estar vacio") }
                    ?: throw ImportException("El modelo no puede ser nulo"),

                descripModelo = descripModelo?.ifBlank { throw ImportException("La descripción no puede quedar vacía") }
                    ?: throw ImportException("La descripción no puede ser nulo"),

                cantidadContenedores = cantidadContenedores?.toIntOrNull()
                    ?: throw ImportException("Las cantidades no son números"),

                lote = lote?.toIntOrNull()
                    ?: throw ImportException("El lote no es un número"),

                distrito = distrito?.ifBlank { throw ImportException("El distrito no puede quedar vacío") }
                    ?: throw ImportException("El distrito no puede ser nulo"),

                barrio = barrio?.ifBlank { "" }
                    ?: throw ImportException("El barrio no puede ser nulo"),

                tipoVia = tipoVia?.ifBlank { throw ImportException("El tipo de vía no puede quedar vacía") }
                    ?: throw ImportException("El tipo de vía no puede ser nulo"),

                nombreVia = nombreVia?.ifBlank { throw ImportException("El nombre de vía no puede quedar vacía") }
                    ?: throw ImportException("El nombre de vía no puede ser nulo"),

                numVia = numVia?.toIntOrNull(),

                coordenadaX = coordenadaX?.toFloatOrNull(),

                coordenadaY = coordenadaY?.toFloatOrNull(),

                longitud = longitud?.ifBlank { throw ImportException("La longitud no puede quedar vacía") }
                    ?: throw ImportException("La longitud no es un número separado por punto"),

                latitud = latitud?.ifBlank { throw ImportException("La latitud no puede quedar vacía") }
                    ?: throw ImportException("La latitud no es un número separado por punto"),

                direccion = direccion?.ifBlank { throw ImportException("La dirección no puede quedar vacía") }
                    ?: throw ImportException("El dirección no puede ser nula")
            )
        }
}