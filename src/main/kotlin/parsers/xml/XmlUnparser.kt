package parsers.xml

import models.Bitacora
import parsers.UnParser
import java.io.OutputStream
import nl.adaptivity.xmlutil.serialization.XML


class XmlUnparser : UnParser<Bitacora> {
    override val extension: String
        get() = ".xml"

    override fun unParse(input: Bitacora, outputStream: OutputStream) {
        XML.encodeToString(input).let { outputStream.write(it.toByteArray()) }
    }
}