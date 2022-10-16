package writers.contenedores

import dto.ContenedorDto
import extensions.toContenedorDto
import importing.contenedores.JsonImporterContenedores
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.Contenedor
import models.TipoContenedor
import org.junit.jupiter.api.Test
import writers.FileWriter
import java.io.File

internal class ContenedoresJsonWriterTest {

    @Test
    fun shouldWrite() {
        val content = sequenceOf(
            Contenedor(
                codIntSitu = "1",
                tipoContenedor = TipoContenedor.ORGANICA,
                modelo = "caca",
                descripModelo = "caca",
                cantidadContenedores = 1,
                lote = 1,
                distrito = "madrid",
                barrio = "barrio",
                tipoVia = "calle",
                nombreVia = "calle",
                numVia = 1,
                coordenadaX = 1.0f,
                coordenadaY = 1.0f,
                longitud = "1",
                latitud = "1",
                direccion = "calle 1"
            )
        )

        val writer = FileWriter("src/test/resources/writtenContenedores.json", JsonImporterContenedores())

        writer.write(content.toContenedorDto())

        val file = File("src/test/resources/writtenContenedores.json")
        assert(file.exists())
        val contenedor: List<ContenedorDto> = Json.decodeFromString(file.readText())
        //TODO ACABAR
    }
}