package writers

import dto.Residuo
import org.junit.jupiter.api.Test
import java.io.File

internal class ResiduoCsvWriterTest {

    @Test
    fun shouldWrite() {
        val writer = ResiduosCsvWriter("src/test/resources/written.csv")
        val content = sequenceOf(
            Residuo(
                1,
                "enero",
                2,
                "caca",
                1,
                "madrid",
                5.2

            )
        )

        writer.write(content)

        val file = File("src/test/resources/written.csv")
        assert(file.exists())
        val lines = file.readLines()
        assert(lines.isNotEmpty())
        assert(lines[0] == "Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas")
        assert(lines[1] == "1;enero;2;caca;1;madrid;5,2")
    }
}