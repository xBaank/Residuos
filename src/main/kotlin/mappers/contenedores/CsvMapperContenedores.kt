package mappers.contenedores

import exceptions.CsvException
import extensions.*
import models.Contenedor
import models.Residuo
import java.time.LocalDate
import java.time.Month
import java.util.*

/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

class CsvMapperContenedores {
    fun mapTo(input: Sequence<String>): Sequence<Unit> = input.drop(1).map { line ->
        val (codIntSitu, tipoContenedor, modelo, descripModelo,
            cantidadContenedores, lote, distrito, tipoVia, nombreVia,
            numVia, longitud, latitud, direccion
        ) = line.split(';')


    }



    fun mapFrom(input: Sequence<Contenedor>): Sequence<String> = sequence {
        yield("CodigoSituado;TipoContenedor;Modelo;Descripcion;Cantidad;Lote;Distrito;" +
                "TipoVia;Nombre;Numero;CoordenadaX;CoordenadaY;Longitud;Latitud;Direccion")
        yieldAll(input.map { contenedores -> "${contenedores.codIntSitu};${contenedores.tipoContenedor};" +
                "${contenedores.modelo};${contenedores.descripModelo};${contenedores.cantidadContenedores};" +
                "${contenedores.lote};${contenedores.distrito};${contenedores.tipoVia};${contenedores.nombreVia};" +
                "${contenedores.numVia};${contenedores.coordenadaX};${contenedores.coordenadaY};${contenedores.longitud};" +
                "${contenedores.latitud};${contenedores.direccion}"

        })
    }
}