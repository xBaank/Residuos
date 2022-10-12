package parsers.contenedores

import aliases.Contenedores
import dto.ContenedorDto
import exceptions.CsvException
import extensions.*
import parsers.formats.CsvParser
import java.io.InputStream
import java.io.OutputStream


/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */


class CsvParserContenedores : CsvParser<Contenedores> {
    override val firstLine: String
        get() = "Código Interno del Situad;Tipo Contenedor;Modelo;Descripcion Modelo;Cantidad;Lote;Distrito;Barrio;Tipo Vía;Nombre;Número;COORDENADA X;COORDENADA Y;LONGITUD;LATITUD;DIRECCION"


    override fun parse(input: InputStream): Sequence<ContenedorDto> =
        input.bufferedReader().lineSequence().filterFirstLine(firstLine).drop(1).map { line ->

            val (codIntSitu, tipoContenedor, modelo, descripModelo, cantidadContenedores, lote, distrito, barrio, tipoVia, nombreVia, numVia, coordenadaX, coordenadaY, longitud, latitud, direccion) = line.split(
                ';'
            )

            ContenedorDto(

                codIntSitu = codIntSitu?.ifBlank { throw CsvException("El codigo no puede estar vacio") }
                    ?: throw CsvException("El codigo no puede ser nulo"),

                tipoContenedor = tipoContenedor?.ifBlank { throw CsvException("El tipo del contenedor nu puede estar vacío") }
                    ?: throw CsvException("El tipo no puede ser nulo"),

                modelo = modelo?.ifBlank { throw CsvException("El modelo no puede estar vacio") }
                    ?: throw CsvException("El modelo no puede ser nulo"),

                descripModelo = descripModelo?.ifBlank { throw CsvException("La descripción no puede quedar vacía") }
                    ?: throw CsvException("La descripción no puede ser nulo"),

                cantidadContenedores = cantidadContenedores?.toIntOrNull()
                    ?: throw CsvException("Las cantidades no son números"),

                lote = lote?.toIntOrNull()
                    ?: throw CsvException("El lote no es un número"),

                distrito = distrito?.ifBlank { throw CsvException("El distrito no puede quedar vacío") }
                    ?: throw CsvException("El distrito no puede ser nulo"),

                barrio = barrio?.ifBlank { "" }
                    ?: throw CsvException("El barrio no puede ser nulo"),

                tipoVia = tipoVia?.ifBlank { throw CsvException("El tipo de vía no puede quedar vacía") }
                    ?: throw CsvException("El tipo de vía no puede ser nulo"),

                nombreVia = nombreVia?.ifBlank { throw CsvException("El nombre de vía no puede quedar vacía") }
                    ?: throw CsvException("El nombre de vía no puede ser nulo"),

                numVia = numVia?.toIntOrNull(),

                coordenadaX = coordenadaX?.toFloatOrNull(),

                coordenadaY = coordenadaY?.toFloatOrNull(),

                longitud = longitud?.ifBlank { throw CsvException("La longitud no puede quedar vacía") }
                    ?: throw CsvException("La longitud no es un número separado por punto"),

                latitud = latitud?.ifBlank { throw CsvException("La latitud no puede quedar vacía") }
                    ?: throw CsvException("La latitud no es un número separado por punto"),

                direccion = direccion?.ifBlank { throw CsvException("La dirección no puede quedar vacía") }
                    ?: throw CsvException("El dirección no puede ser nula")
            )


        }


    override fun unParse(input: Sequence<ContenedorDto>, outputStream: OutputStream) =
        outputStream.bufferedWriter().run {
            appendLine(
                "CodigoSituado;TipoContenedor;Modelo;Descripcion;Cantidad;Lote;Distrito;Barrio;" +
                        "TipoVia;Nombre;Numero;CoordenadaX;CoordenadaY;Longitud;Latitud;Direccion"
            )
            input.map { contenedores ->
                "${contenedores.codIntSitu};${contenedores.tipoContenedor};" +
                        "${contenedores.modelo};${contenedores.descripModelo};${contenedores.cantidadContenedores};" +
                        "${contenedores.lote};${contenedores.distrito};${contenedores.barrio};${contenedores.tipoVia};${contenedores.nombreVia};" +
                        "${contenedores.numVia};${contenedores.coordenadaX};${contenedores.coordenadaY};${contenedores.longitud};" +
                        "${contenedores.latitud};${contenedores.direccion}"

            }.forEach { appendLine(it) }

            flush()
        }
}