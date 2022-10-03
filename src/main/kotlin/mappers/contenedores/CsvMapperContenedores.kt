package mappers.contenedores

import extensions.*
import models.Contenedor
import exceptions.CsvException


/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

object CsvMapperContenedores {
    fun mapTo(input: Sequence<String>): Sequence<Contenedor> = input.drop(1).map { line ->

        val (codIntSitu, tipoContenedor, modelo, descripModelo,
            cantidadContenedores, lote, distrito, barrio,tipoVia, nombreVia,
            numVia, coordenadaX, coordenadaY, latitud, longitud, direccion
        ) = line.split(';')

    Contenedor(

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

        barrio = barrio?.ifBlank { throw CsvException("El barrio no puede quedar vacío") }
            ?: throw CsvException("El barrio no puede ser nulo"),

        tipoVia = tipoVia?.ifBlank { throw CsvException("El tipo de vía no puede quedar vacía") }
            ?: throw CsvException("El tipo de vía no puede ser nulo"),

        nombreVia = nombreVia?.ifBlank { throw CsvException("El nombre de vía no puede quedar vacía") }
            ?: throw CsvException("El nombre de vía no puede ser nulo"),

        numVia = numVia?.toIntOrNull()
            ?: throw CsvException("El numero de vía no es un número"),

        coordenadaX = coordenadaX?.toLongOrNull()
            ?: throw CsvException("La coordenada no es un número"),

        coordenadaY = coordenadaY?.toLongOrNull()
            ?: throw CsvException("La coordenada no es un número"),

        longitud = longitud?.toIntOrNull()
            ?: throw CsvException("La longitud no es un número"),

        latitud = latitud?.toIntOrNull()
            ?: throw CsvException("La latitud no es un número"),

        direccion = direccion?.ifBlank { throw CsvException("La dirección no puede quedar vacía") }
            ?: throw CsvException("El dirección no puede ser nula")
    )


    }


    fun mapFrom(input: Sequence<Contenedor>): Sequence<String> = sequence {
        yield("CodigoSituado;TipoContenedor;Modelo;Descripcion;Cantidad;Lote;Distrito;" +
                "TipoVia;Nombre;Numero;CoordenadaX;CoordenadaY;Longitud;Latitud;Direccion")
        yieldAll(input.map { contenedores -> "${contenedores.codIntSitu};${contenedores.tipoContenedor};" +
                "${contenedores.modelo};${contenedores.descripModelo};${contenedores.cantidadContenedores};" +
                "${contenedores.lote};${contenedores.distrito};${contenedores.barrio};${contenedores.tipoVia};${contenedores.nombreVia};" +
                "${contenedores.numVia};${contenedores.coordenadaX};${contenedores.coordenadaY};${contenedores.longitud};" +
                "${contenedores.latitud};${contenedores.direccion}"

        })
    }
}