package readers

import mappers.contenedores.CsvMapperContenedores
import models.Contenedor
import java.io.File
import java.io.FileNotFoundException

class ContenedoresCsvReader (private val path: String) : Reader<Contenedor> {
    private val mapper = CsvMapperContenedores()
    override fun read(): Sequence<Contenedor> = sequence {
       File(path)
           .apply { if (!exists()) throw FileNotFoundException() }
           .useLines {yieldAll(mapper.mapTo(it))}
    }
}