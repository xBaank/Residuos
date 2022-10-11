package parsers

import java.io.InputStream

interface Parser<out T> {
    val extension: String

    /**
     * Read from an input stream and return a type
     * *NOTE* It's the caller responsibility to close the input stream
     */
    fun parse(input: InputStream): T


}