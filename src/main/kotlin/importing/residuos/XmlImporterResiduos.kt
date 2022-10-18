package importing.residuos

import aliases.Residuos
import dto.ResiduoDto
import exceptions.ImportException
import extensions.import
import formats.IXmlImporter
import nl.adaptivity.xmlutil.serialization.XML
import java.io.InputStream

class XmlImporterResiduos(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlImporter<Residuos> {
    override fun import(input: InputStream): Sequence<ResiduoDto> = sequence {
        kotlin.runCatching {
            xml.import<ResiduoDto>(input).forEach { yield(it) }
        }.onFailure {
            throw ImportException("Error al importar los residuos en formato xml")
        }

    }
}