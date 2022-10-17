package writers

interface IWriter<T> {
    val path: String
    val formats: List<String>
    val name: String

    suspend fun write(content: T)
}