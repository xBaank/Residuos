package importing.contenedores


import aliases.Contenedores
import dto.ContenedorDto
import exceptions.ImportException
import formats.IJsonImporter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import java.io.InputStream
import java.util.*


class JsonImporterContenedores(private val json: Json = Json { prettyPrint = true }) : IJsonImporter<Contenedores> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun import(input: InputStream): Sequence<ContenedorDto> = sequence {
        kotlin.runCatching {
            json.decodeToSequence<ContenedorDto>(input).forEach { yield(it) }
        }.onFailure {
            throw ImportException("Error al importar los contenedores en formato json")
        }
    }
}

