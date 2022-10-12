package args

import exceptions.ArgsException

class OpcionResumen(params: Array<String>) : Opcion {

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

            else -> throw ArgsException("La opción no es válida")
        }
    }
}