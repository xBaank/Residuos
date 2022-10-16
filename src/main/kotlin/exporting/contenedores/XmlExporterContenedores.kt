package exporting.contenedores

import aliases.Contenedores
import dto.ContenedorDto
import formats.IXmlExporter
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
        xml.encodeToString(input.toList()).let { string ->
            outputStream.bufferedWriter().let {
                it.write(string)
                it.flush()
            }
        }
}