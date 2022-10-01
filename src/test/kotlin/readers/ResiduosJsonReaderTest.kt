package readers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException

internal class ResiduosJsonReaderTest {

    @Test
    fun shouldRead() {
        val reader = ResiduosJsonReader("src/test/resources/residuos.json")

        val data = reader.read()
        val residuo = data.firstOrNull()

        assert(data.count() == 1)
        assert(residuo?.lote == 2)
        assert(residuo?.residuo == "caca")
        assert(residuo?.distrito == 1)
        assert(residuo?.nombreDistrito == "madrid")
        assert(residuo?.toneladas == 5.2)
    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = ResiduosJsonReader("src/test/resources/badResiduosasdasd.csv")

        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}