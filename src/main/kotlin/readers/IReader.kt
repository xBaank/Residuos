package readers

interface IReader<out T> {
    val path: String
    val formats: List<String>
    val name: String

    suspend fun read(): T
}