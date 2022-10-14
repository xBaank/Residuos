package core.exporting.residuos

import aliases.Residuos
import core.formats.IXmlExporter
import dto.ResiduoDto
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
        xml.encodeToString(input.toList()).let { outputStream.write(it.toByteArray()) }
}