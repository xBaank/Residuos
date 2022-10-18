package importing.contenedores

import aliases.Contenedores
import dto.ContenedorDto
import exceptions.ImportException
import extensions.import
import formats.IXmlImporter
import nl.adaptivity.xmlutil.serialization.XML
import java.io.InputStream

class XmlImporterContenedores(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlImporter<Contenedores> {
    override fun import(input: InputStream): Sequence<ContenedorDto> = sequence {
        kotlin.runCatching {
            xml.import<ContenedorDto>(input).forEach { yield(it) }
        }.onFailure {
            throw ImportException("Error al importar los contenedores en formato xml")
        }
    }
}