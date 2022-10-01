package writers

import mappers.residuos.JsonMapper
import models.Residuo
import java.io.File
import java.nio.file.Files

class ResiduosJsonWriter(private val path: String) : Writer<Residuo> {
    private val mapper = JsonMapper()

    override fun write(content: Sequence<Residuo>) {
        File(path)
            .apply { if (exists()) Files.delete(toPath()) }
            .apply { createNewFile() }
            .apply {
                outputStream().use { output -> mapper.mapFrom(content).use { input -> input.copyTo(output) } }
            }
    }
}