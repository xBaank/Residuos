package readers.contenedores

import extensions.toContenedor
import importing.contenedores.XmlImporterContenedores
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import readers.FileReader
import java.io.FileNotFoundException

internal class ContenedorXmlReaderTest {

    @Test
    fun shouldRead() {
        val reader = FileReader("src/test/resources/contenedores.xml", XmlImporterContenedores())

        val data = reader.read().toContenedor()
//        val contenedor = data.firstOrNull()
        //TODO ACABAR
    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = FileReader("src/test/resources/asdcontenedores.xml", XmlImporterContenedores())

        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}