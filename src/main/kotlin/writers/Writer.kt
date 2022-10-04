package writers

interface Writer<in T> {
    /**
     * Writes a sequence of objects to a destination
     */
    fun write(content: Sequence<T>)
}