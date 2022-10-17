package readers

interface IReader<T> {
    val path: String
    val formats: List<String>
    val name: String

    suspend fun read(): T
}