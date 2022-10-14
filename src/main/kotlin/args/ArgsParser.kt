package args

import exceptions.ArgsException

private const val correctFormat =
    "debe ser: parser <directorioOrigen> <directorioDestino> o resumen <directorioOrigen> <directorioDestino> o resumen <distrito> <directorioOrigen> <directorioDestino>"

class ArgsParser(private val params: Array<String>) {
    init {
        if (params.isEmpty())
            throw ArgsException(
                "No se han introducido parámetros, $correctFormat"
            )
    }

    fun parse(): Opcion = when (params.first().lowercase()) {
        "parser" -> OpcionParser(params)
        "resumen" -> OpcionResumen(params)
        else -> throw ArgsException(
            "Los argumentos ${params.joinToString(" ")} no son validos, $correctFormat"
        )
    }
}