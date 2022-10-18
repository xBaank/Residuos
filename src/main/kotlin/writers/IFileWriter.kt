package writers


/**
 * Interfaz para escribir datos en un archivo
 */
interface IFileWriter<in T> {
    val path: String
    val formats: List<String>
    val name: String

    suspend fun write(content: T)
}