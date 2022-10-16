package exporting.residuos

import aliases.Residuos
import dto.ResiduoDto
import formats.IXmlExporter
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import java.io.OutputStream

class XmlExporterResiduos(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlExporter<Residuos> {
    override fun export(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        xml.encodeToString(input.toList()).let { string ->
            outputStream.bufferedWriter().let {
                it.write(string)
                it.flush()
            }
        }
}