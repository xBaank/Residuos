package writers

import mappers.contenedores.CsvMapperContenedores
import models.Contenedor
import java.io.File
import java.nio.file.Files

class ContenedoresCsvWriter(private val path: String) : Writer<Contenedor> {
    private val mapper = CsvMapperContenedores

    override fun write(content: Sequence<Contenedor>) = File(path)
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .printWriter()
        .use { out -> mapper.mapFrom(content).forEach { out.println(it) } }
}