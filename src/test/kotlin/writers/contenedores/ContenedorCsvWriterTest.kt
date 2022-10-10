package writers.contenedores

import extensions.toContenedorDto
import models.Contenedor
import models.TipoContenedor
import org.junit.jupiter.api.Test
import parsers.contenedores.CsvParserContenedores
import writers.FileWriter
import java.io.File

internal class ContenedorCsvWriterTest {
    @Test
    fun shouldWrite() {
        val writer = FileWriter("src/test/resources/writtenContenedores.csv", CsvParserContenedores())
        val content = sequenceOf(
            Contenedor(
                "1",
                 TipoContenedor.RESTO,
                "caca",
                "caca",
                1,
                1,
                "madrid",
                "barrio",
                "calle",
                "calle",
                1,
                1.0f,
                1.0f,
                "1",
                "1",
                "calle 1"
            )
        )

        writer.write(content.toContenedorDto())

        val file = File("src/test/resources/writtenContenedores.csv")
        assert(file.exists())
        val lines = file.readLines()
        assert(lines.isNotEmpty())
        assert(lines[0] == "CodigoSituado;TipoContenedor;Modelo;Descripcion;Cantidad;Lote;Distrito;Barrio;TipoVia;" +
                "Nombre;Numero;CoordenadaX;CoordenadaY;Longitud;Latitud;Direccion")
        assert(lines[1] == "1;RESTO;caca;caca;1;1;madrid;barrio;calle;calle;1;1.0;1.0;1;1;calle 1")
    }
}