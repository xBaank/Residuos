package controllers

import exceptions.ArgsException
import extensions.getArgument
import extensions.removeArguments
import options.Opcion
import options.OpcionParser
import options.OpcionResumen
import options.optionalArguments

private const val correctFormat = """

FORMATO CORRECTO: 
parser <directorioOrigen> <directorioDestino> o resumen <directorioOrigen> <directorioDestino> o resumen <distrito> <directorioOrigen> <directorioDestino>
"""

/**
 * Clase para parsear los argumentos de la linea de comandos
 */
class ArgsController(private var params: Array<String>) : IController<Opcion> {
    init {
        if (params.isEmpty())
            throw ArgsException(
                "No se han introducido parámetros $correctFormat $optionalArguments"
            )
    }

    override suspend fun process(): Opcion {

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