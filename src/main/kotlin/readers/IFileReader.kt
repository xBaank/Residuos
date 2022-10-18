package readers

/**
 * Interfaz para leer datos de un archivo
 */
interface IFileReader<out T> {
    val path: String
    val formats: List<String>
    val name: String

    suspend fun read(): T
}