package exporting.contenedores

import aliases.Contenedores
import dto.ContenedorDto
import extensions.export
import formats.IXmlExporter
import nl.adaptivity.xmlutil.serialization.XML
import java.io.OutputStream

class XmlExporterContenedores(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlExporter<Contenedores> {

    override fun export(input: Sequence<ContenedorDto>, outputStream: OutputStream) =
        xml.export(input.toList(), outputStream)
}