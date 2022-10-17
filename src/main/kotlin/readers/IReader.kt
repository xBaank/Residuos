package readers

interface IReader<T> {
    suspend fun read(): Sequence<T>
}