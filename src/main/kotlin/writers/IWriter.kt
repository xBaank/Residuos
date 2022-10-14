package writers

interface IWriter<T> {
    suspend fun write(content: T)
}