package importing.contenedores


import aliases.Contenedores
import dto.ContenedorDto
import formats.IJsonImporter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import java.io.InputStream
import java.util.*


class JsonImporterContenedores(private val json: Json = Json { prettyPrint = true }) : IJsonImporter<Contenedores> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun import(input: InputStream): Sequence<ContenedorDto> = json.decodeToSequence(input)
}

