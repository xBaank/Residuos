package readers

/**
 * Interfaz para leer datos de un archivo
 */
interface IFileReader<out T> {
    val path: String
    val formats: List<String>
    val name: String

    /**
     * Lee los datos del archivo
     */
    suspend fun read(): T
}