package readers

import mappers.residuos.JsonMapper
import models.Residuo
import java.io.File
import java.io.FileNotFoundException

class ResiduosJsonReader(private val path: String) : Reader<Residuo> {
    private val maper = JsonMapper

    override fun read(): Sequence<Residuo> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .run { inputStream().use { yieldAll(maper.mapTo(it)) } }
    }
}