package exporting.residuos

import dto.ResiduoDto
import formats.residuos.ICsvExporterResiduos
import java.io.OutputStream

/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

class CsvExporterResiduos : ICsvExporterResiduos {
    override fun export(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        outputStream.bufferedWriter().run {
            appendLine(firstLine)
            input.map { residuo ->
                "${residuo.ano};${residuo.mes};${residuo.lote};${residuo.residuo};${residuo.distrito};${residuo.nombreDistrito};${
                    residuo.toneladas.toString().replace('.', ',')
                }"
            }.forEach { appendLine(it) }

            flush()
        }

}
