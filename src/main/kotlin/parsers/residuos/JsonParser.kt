package parsers.residuos


import dto.ResiduoDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class JsonParser(private val json: Json = Json { prettyPrint = true }) : Parser<ResiduoDto> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun parse(input: InputStream): Sequence<ResiduoDto> = json.decodeToSequence(input)

    @OptIn(ExperimentalSerializationApi::class)
    override fun unParse(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        json.encodeToStream(input.toList(), outputStream)
}

