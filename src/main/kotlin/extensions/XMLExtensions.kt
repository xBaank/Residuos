package extensions

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import java.io.InputStream
import java.io.OutputStream

inline fun <reified T> XML.export(input: T, outputStream: OutputStream) {
    encodeToString(input).let { string ->
        outputStream.bufferedWriter().let {
            it.write(string)
            it.flush()
        }
    }
}

inline fun <reified T> XML.import(input: InputStream): Sequence<T> =
    decodeFromString<List<T>>(input.reader().readText()).asSequence()