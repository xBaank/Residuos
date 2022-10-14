package core.exporting.contenedores

import aliases.Contenedores
import core.formats.IXmlExporter
import dto.ContenedorDto
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import java.io.OutputStream

class XmlExporterContenedores(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlExporter<Contenedores> {

    override fun export(input: Sequence<ContenedorDto>, outputStream: OutputStream) =
        xml.encodeToString(input.toList()).let { outputStream.write(it.toByteArray()) }
}