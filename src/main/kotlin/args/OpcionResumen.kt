package args

import exceptions.ArgsException


private const val correctFormat = """

FORMATO CORRECTO:
resumen <directorioOrigen> <directorioDestino> o resumen <distrito> <directorioOrigen> <directorioDestino>
"""


class OpcionResumen(
    params: Array<String>,
    override val residuosFile: String?,
    override val contenedoresFile: String?,
) : Opcion {

    override fun toString(): String = "Resumen ${if (distrito == null) "global" else "$distrito"}"

    private var _distrito: String? = null
    private var _directorioOrigen: String
    private var _directorioDestino: String

    override val directorioOrigen
        get() =
            _directorioOrigen

    override val directorioDestino
        get() = _directorioDestino

    val distrito
        get() = _distrito

    init {
        when (params.size) {
            4 -> {
                _distrito = params[1]
                _directorioOrigen = params[2]
                _directorioDestino = params[3]
            }

            3 -> {
                _directorioOrigen = params[1]
                _directorioDestino = params[2]
            }

            else -> throw ArgsException(
                "Formato incorrecto para la opción resumen $correctFormat $optionalArguments"
            )
        }
    }
}