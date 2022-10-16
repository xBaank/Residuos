package importing.contenedores

import aliases.Contenedores
import dto.ContenedorDto
import formats.IXmlImporter
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML
import java.io.InputStream

class XmlImporterContenedores(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlImporter<Contenedores> {
    override fun import(input: InputStream): Sequence<ContenedorDto> =
        xml.decodeFromString<List<ContenedorDto>>(input.reader().readText()).asSequence()
}