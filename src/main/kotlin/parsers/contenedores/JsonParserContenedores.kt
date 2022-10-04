package parsers.contenedores


import dto.ContenedorDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import kotlinx.serialization.json.encodeToStream
import parsers.Parser
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class JsonParserContenedores(private val json: Json = Json { prettyPrint = true }) : Parser<ContenedorDto> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun parse(input: InputStream): Sequence<ContenedorDto> = json.decodeToSequence(input)

    @OptIn(ExperimentalSerializationApi::class)
    override fun unParse(input: Sequence<ContenedorDto>, outputStream: OutputStream) =
        json.encodeToStream(input.toList(), outputStream)
}

