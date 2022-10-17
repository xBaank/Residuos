package args

import exceptions.ArgsException
import extensions.getArgument
import extensions.removeArguments

private const val correctFormat = """

FORMATO CORRECTO: 
parser <directorioOrigen> <directorioDestino> o resumen <directorioOrigen> <directorioDestino> o resumen <distrito> <directorioOrigen> <directorioDestino>
"""

class ArgsParser(private var params: Array<String>) {
    init {
        if (params.isEmpty())
            throw ArgsException(
                "No se han introducido parámetros $correctFormat $optionalArguments"
            )
    }

    fun parse(): Opcion {

        val residuosFile = params.getArgument("-residuos=")
        val contenedoresFile = params.getArgument("-contenedores=")
        params = params.removeArguments("-residuos=", "-contenedores=")

        return when (params.first().lowercase()) {
            "parser" -> OpcionParser(params, residuosFile, contenedoresFile)
            "resumen" -> OpcionResumen(params, residuosFile, contenedoresFile)
            else -> throw ArgsException(
                "Formato incorrecto $correctFormat $optionalArguments"
            )
        }
    }
}