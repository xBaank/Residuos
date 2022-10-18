package exporting.xml

import extensions.export
import formats.IXmlExporter
import models.Bitacora
import nl.adaptivity.xmlutil.serialization.XML
import java.io.OutputStream


class BitacoraExporter(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlExporter<Bitacora> {

    override fun export(input: Bitacora, outputStream: OutputStream) = xml.export(input, outputStream)
}