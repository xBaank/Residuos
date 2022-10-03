package writers

import mappers.residuos.JsonMapper
import models.Contenedor
import java.io.File
import java.nio.file.Files

class ContenedoresJsonWriter(private val path: String) : Writer<Contenedor> {
    private val mapper = JsonMapper

    override fun write(content: Sequence<Contenedor>) {
        File(path)
            .apply { if (exists()) Files.delete(toPath()) }
            .apply { createNewFile() }
            .apply {
                outputStream().use { output -> mapper.mapFrom(content).use { input -> input.copyTo(output) } }
            }
    }
}