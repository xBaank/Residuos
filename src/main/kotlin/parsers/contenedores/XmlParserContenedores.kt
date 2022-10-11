package parsers.contenedores

import aliases.Contenedores
import dto.ContenedorDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import parsers.formats.XmlParser
import java.io.InputStream
import java.io.OutputStream

class XmlParserContenedores(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    }
) : XmlParser<Contenedores> {
    override fun parse(input: InputStream): Sequence<ContenedorDto> =
        xml.decodeFromString<List<ContenedorDto>>(input.reader().readText()).asSequence()

    override fun unParse(input: Sequence<ContenedorDto>, outputStream: OutputStream) =
        xml.encodeToString(input.toList()).let { outputStream.write(it.toByteArray()) }
}