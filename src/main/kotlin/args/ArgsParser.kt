package args

import args.OpcionConfig.OpcionParser
import args.OpcionConfig.OpcionResumen
import exceptions.ArgsException
import java.util.*

class ArgsParser(private val params: Array<String>) {
    init {
        if (params.isEmpty())
            throw ArgsException("No se han introducido parámetros")
    }

    fun parse(): OpcionConfig = when (params.first().lowercase(Locale.getDefault())) {
        "parser" -> OpcionParser(params)
        "resumen" -> OpcionResumen(params)
        else -> throw ArgsException("La opción no es válida")
    }
}