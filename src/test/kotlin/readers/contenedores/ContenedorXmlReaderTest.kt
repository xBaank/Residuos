package readers.contenedores

import extensions.toContenedor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parsers.contenedores.XmlParserContenedores
import readers.FileReader
import java.io.FileNotFoundException

internal class ContenedorXmlReaderTest {

    @Test
    fun shouldRead() {
        val reader = FileReader("src/test/resources/contenedores.xml", XmlParserContenedores())

        val data = reader.read().toContenedor()
//        val contenedor = data.firstOrNull()
        //TODO ACABAR
    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = FileReader("src/test/resources/asdcontenedores.xml", XmlParserContenedores())

        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}