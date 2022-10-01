package readers

import exceptions.CsvException
import extensions.fecha
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException
import java.time.LocalDate

internal class ResiduoCsvReaderTest {

    @Test
    fun shouldRead() {
        val reader = ResiduosCsvReader("src/test/resources/residuos.csv")

        val data = reader.read()
        val expected = data.firstOrNull()

        assert(data.count() == 2138)
        assert(expected?.ano == 2021)
        assert(expected?.mes == "enero")
        assert(expected?.lote == 1)
        assert(expected?.residuo == "RESTO")
        assert(expected?.distrito == 1)
        assert(expected?.nombreDistrito == "Centro")
        assert(expected?.toneladas == 3477.92)
        assert(expected?.fecha == LocalDate.of(2021, 1, 1))
    }

    @Test
    fun shouldNotRead() {
        val reader = ResiduosCsvReader("src/test/resources/badResiduos.csv")

        assertThrows<CsvException> { reader.read().toList() }
    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = ResiduosCsvReader("src/test/resources/badResiduosasdasd.csv")

        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}