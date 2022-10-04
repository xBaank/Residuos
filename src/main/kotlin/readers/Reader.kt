package readers

interface Reader<out T> {
    /**
     * Reads from an origin and returns a sequence of objects
     */
    fun read(): Sequence<T>
}