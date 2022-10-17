package args

import exceptions.ArgsException

private const val correctFormat = """

FORMATO CORRECTO:
parser <directorioOrigen> <directorioDestino>
"""

class OpcionParser(
    params: Array<String>,
    override val residuosFile: String?,
    override val contenedoresFile: String?,
) :
    Opcion {

    override fun toString(): String = "Parser"

    init {
        if (params.size != 3)
            throw ArgsException("Formato incorrecto para la opción parser $correctFormat $optionalArguments")
    }


    override val directorioOrigen = params[1]
    override val directorioDestino = params[2]
}