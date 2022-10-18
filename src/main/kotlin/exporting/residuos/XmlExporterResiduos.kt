package exporting.residuos

import aliases.Residuos
import dto.ResiduoDto
import extensions.export
import formats.IXmlExporter
import nl.adaptivity.xmlutil.serialization.XML
import java.io.OutputStream

class XmlExporterResiduos(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlExporter<Residuos> {
    override fun export(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        xml.export(input.toList(), outputStream)
}