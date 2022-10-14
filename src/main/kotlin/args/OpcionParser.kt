package args

import exceptions.ArgsException

private const val correctFormat = "debe ser: parser <directorioOrigen> <directorioDestino>"

class OpcionParser(params: Array<String>) : Opcion {

    override fun toString(): String = "Parser"

    init {
        if (params.size != 3)
            throw ArgsException("Formato incorrecto para la opción parser, $correctFormat")
    }

    override val directorioOrigen = params[1]
    override val directorioDestino = params[2]
}