package writers

import dto.ContenedorDto
import parsers.contenedores.CsvParserContenedores
import parsers.contenedores.JsonParserContenedores
import parsers.contenedores.XmlParserContenedores
import java.io.File
import java.util.*

class ContenedoresFileWriter(private var path: String) : Writer<ContenedorDto> {
    private val name: String = UUID.randomUUID().toString()

    init {
        path = File(path)
            .apply { if (isFile) throw IllegalArgumentException("El directorio destino no puede ser un archivo") }
            .apply { if (!canWrite()) throw IllegalArgumentException("No se puede escribir en el directorio destino") }
            .apply { isDirectory || mkdirs() }
            .path
    }

    private val jsonParserContenedores = JsonParserContenedores()
    private val csvParserContenedores = CsvParserContenedores()
    private val xmlParserContenedores = XmlParserContenedores()

    private val jsonWriterContenedores = FileWriter("$path${File.separator}$name.json", jsonParserContenedores)
    private val csvWriterContenedores = FileWriter("$path${File.separator}$name.csv", csvParserContenedores)
    private val xmlWriterContenedores = FileWriter("$path${File.separator}$name.xml", xmlParserContenedores)

    override fun write(content: Sequence<ContenedorDto>) {
        csvWriterContenedores.write(content)
        jsonWriterContenedores.write(content)
        xmlWriterContenedores.write(content)
    }


}