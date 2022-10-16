package exporting.contenedores

import dto.ContenedorDto
import formats.contenedores.ICsvExporterContenedores
import java.io.OutputStream


/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */


class CsvExporterContenedores : ICsvExporterContenedores {
    override fun export(input: Sequence<ContenedorDto>, outputStream: OutputStream) =
        outputStream.bufferedWriter().run {
            appendLine(firstLine)
            input.map { contenedores ->
                "${contenedores.codIntSitu};${contenedores.tipoContenedor};" +
                        "${contenedores.modelo};${contenedores.descripModelo};${contenedores.cantidadContenedores};" +
                        "${contenedores.lote};${contenedores.distrito};${contenedores.barrio};${contenedores.tipoVia};${contenedores.nombreVia};" +
                        "${contenedores.numVia};${contenedores.coordenadaX};${contenedores.coordenadaY};${contenedores.longitud};" +
                        "${contenedores.latitud};${contenedores.direccion}"

            }.forEach { appendLine(it) }
            flush()
        }
}