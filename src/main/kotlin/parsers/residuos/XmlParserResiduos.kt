package parsers.residuos

import aliases.Residuos
import dto.ResiduoDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import parsers.formats.XmlParser
import java.io.InputStream
import java.io.OutputStream

class XmlParserResiduos(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    }
) : XmlParser<Residuos> {
    override fun parse(input: InputStream): Sequence<ResiduoDto> =
        xml.decodeFromString<List<ResiduoDto>>(input.reader().readText()).asSequence()

    override fun unParse(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        xml.encodeToString(input.toList()).let { outputStream.write(it.toByteArray()) }
}