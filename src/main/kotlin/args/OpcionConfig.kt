package args

import exceptions.ArgsException

sealed interface OpcionConfig {
    val directorioOrigen: String
    val directorioDestino: String

    class OpcionParser(params: Array<String>) : OpcionConfig {
        init {
            if (params.size != 3)
                throw ArgsException("La opción no es válida")
        }

        override val directorioOrigen = params[1]
        override val directorioDestino = params[2]
    }

    class OpcionResumen(params: Array<String>) : OpcionConfig {

        private var _distrito: String? = null
        private var _directorioOrigen: String? = null
        private var _directorioDestino: String? = null

        override val directorioOrigen
            get() =
                _directorioOrigen ?: throw ArgsException("No se ha introducido el directorio de origen")

        override val directorioDestino
            get() =
                _directorioDestino ?: throw ArgsException("No se ha introducido el directorio de destino")

        val distrito = _distrito

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
}

