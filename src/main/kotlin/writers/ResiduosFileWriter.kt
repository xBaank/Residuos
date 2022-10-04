package writers

import dto.ResiduoDto
import parsers.residuos.CsvParserResiduos
import parsers.residuos.JsonParserResiduos
import parsers.residuos.XmlParserResiduos
import java.io.File
import java.util.*

class ResiduosFileWriter(private var path: String) : Writer<ResiduoDto> {
    private val name: String = UUID.randomUUID().toString()

    init {
        path = File(path)
            .apply { if (isFile) throw IllegalArgumentException("El directorio destino no puede ser un archivo") }
            .apply { if (!canWrite()) throw IllegalArgumentException("No se puede escribir en el directorio destino") }
            .apply { isDirectory || mkdirs() }
            .path
    }

    private val jsonParser = JsonParserResiduos()
    private val csvParserResiduos = CsvParserResiduos()
    private val xmlParser = XmlParserResiduos()

    private val jsonWriter = FileWriter("$path${File.separator}$name.json", jsonParser)
    private val csvWriter = FileWriter("$path${File.separator}$name.csv", csvParserResiduos)
    private val xmlWriter = FileWriter("$path${File.separator}$name.xml", xmlParser)

    override fun write(content: Sequence<ResiduoDto>) {
        csvWriter.write(content)
        jsonWriter.write(content)
        xmlWriter.write(content)
    }
}