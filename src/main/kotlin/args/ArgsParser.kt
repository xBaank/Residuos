package args

import exceptions.ArgsException

class ArgsParser(private val params: Array<String>) {
    init {
        if (params.isEmpty())
            throw ArgsException("No se han introducido parámetros")
    }

    fun parse(): Opcion = when (params.first().lowercase()) {
        "parser" -> OpcionParser(params)
        "resumen" -> OpcionResumen(params)
        else -> throw ArgsException("La opción no es válida")
    }
}