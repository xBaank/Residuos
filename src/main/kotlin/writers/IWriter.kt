package writers

interface IWriter<in T> {
    val path: String
    val formats: List<String>
    val name: String

    suspend fun write(content: T)
}