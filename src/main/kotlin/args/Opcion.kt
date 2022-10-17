package args


const val optionalArguments = """
PARAMETROS OPCIONALES:
-residuos=<archivo> para especificar el archivo de residuos
-contenedores=<archivo> para especificar el archivo de contenedores
en caso de no especificarlos, se intentarán autodetectar los archivos en formato csv

EJEMPLO: 
parser -residuos=residuos.json -contenedores=contenedores.xml ./ ./ParsedFiles
"""

sealed interface Opcion {
    val residuosFile: String?
    val contenedoresFile: String?
    val directorioOrigen: String
    val directorioDestino: String
}

