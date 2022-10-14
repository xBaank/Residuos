package core.exporting.residuos


import aliases.Residuos
import core.formats.IJsonExporter
import dto.ResiduoDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.OutputStream
import java.util.*


class JsonExporterResiduos(private val json: Json = Json { prettyPrint = true }) : IJsonExporter<Residuos> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun export(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        json.encodeToStream(input.toList(), outputStream)
}

