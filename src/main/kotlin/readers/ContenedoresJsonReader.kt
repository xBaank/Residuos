package readers

import mappers.residuos.JsonMapper
import models.Contenedor
import java.io.File
import java.io.FileNotFoundException

class ContenedoresJsonReader(private val path: String) : Reader<Contenedor> {
    private val maper = JsonMapper

    override fun read(): Sequence<Contenedor> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .run { inputStream().use { yieldAll(maper.mapTo(it)) } }
    }
}