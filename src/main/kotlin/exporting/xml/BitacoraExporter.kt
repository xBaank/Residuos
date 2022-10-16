package exporting.xml

import formats.IXmlExporter
import kotlinx.serialization.encodeToString
import models.Bitacora
import nl.adaptivity.xmlutil.serialization.XML
import java.io.OutputStream


class BitacoraExporter(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlExporter<Bitacora> {

    override fun export(input: Bitacora, outputStream: OutputStream) {
        xml.encodeToString(input).let { string ->
            outputStream.bufferedWriter().let {
                it.write(string)
                it.flush()
            }
        }
    }
}