package args

import exceptions.ArgsException

class OpcionParser(params: Array<String>) : Opcion {
    init {
        if (params.size != 3)
            throw ArgsException("La opción no es válida")
    }

    override val directorioOrigen = params[1]
    override val directorioDestino = params[2]
}