package mappers.residuos


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import java.io.InputStream
import java.util.*


object JsonMapper {

    val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> mapTo(input: InputStream): Sequence<T> = json.decodeToSequence(input)

    inline fun <reified T> mapFrom(input: Sequence<T>): InputStream =
        json.encodeToString(input.toList()).byteInputStream()
}

