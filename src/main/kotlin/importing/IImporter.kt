package importing

import java.io.InputStream

interface IImporter<out T> {
    val extension: String

    /**
     * Read from an input stream and return a type
     * *NOTE* It's the caller responsibility to close the input stream
     */
    fun import(input: InputStream): T


}