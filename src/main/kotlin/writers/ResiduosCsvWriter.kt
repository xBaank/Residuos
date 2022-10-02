package writers

import mappers.residuos.CsvMapperResiduos
import models.Residuo
import java.io.File
import java.nio.file.Files

class ResiduosCsvWriter(private val path: String) : Writer<Residuo> {
    private val mapper = CsvMapperResiduos

    override fun write(content: Sequence<Residuo>) = File(path)
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .printWriter()
        .use { out -> mapper.mapFrom(content).forEach { out.println(it) } }
}