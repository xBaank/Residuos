package writers.residuos

import extensions.toResiduoDto
import models.Residuo
import org.junit.jupiter.api.Test
import parsers.residuos.CsvParserResiduos
import writers.FileWriter
import java.io.File
import java.time.LocalDate
import java.time.Month

internal class ResiduoCsvWriterTest {

    @Test
    fun shouldWrite() {
        val writer = FileWriter("src/test/resources/written.csv", CsvParserResiduos())
        val content = sequenceOf(
            Residuo(
                LocalDate.of(1, Month.JANUARY.value, 1),
                2,
                "caca",
                1,
                "madrid",
                5.2

            )
        )

        writer.write(content.toResiduoDto())

        val file = File("src/test/resources/written.csv")
        assert(file.exists())
        val lines = file.readLines()
        assert(lines.isNotEmpty())
        assert(lines[0] == "Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas")
        assert(lines[1] == "1;enero;2;caca;1;madrid;5,2")
    }
}