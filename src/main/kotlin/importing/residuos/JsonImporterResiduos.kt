package importing.residuos


import aliases.Residuos
import dto.ResiduoDto
import exceptions.ImportException
import formats.IJsonImporter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import java.io.InputStream
import java.util.*


class JsonImporterResiduos(private val json: Json = Json { prettyPrint = true }) : IJsonImporter<Residuos> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun import(input: InputStream): Sequence<ResiduoDto> = sequence {
        kotlin.runCatching {
            json.decodeToSequence<ResiduoDto>(input).forEach { yield(it) }
        }.onFailure {
            throw ImportException("Error al importar los contenedores en formato json")
        }
    }
}

