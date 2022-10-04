package parsers.residuos

import dto.ResiduoDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import parsers.Parser
import java.io.InputStream
import java.io.OutputStream

class XmlParserResiduos(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    }
) : Parser<ResiduoDto> {
    override fun parse(input: InputStream): Sequence<ResiduoDto> =
        xml.decodeFromString<List<ResiduoDto>>(input.reader().readText()).asSequence()

    override fun unParse(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        xml.encodeToString(input.toList()).let { outputStream.write(it.toByteArray()) }
}