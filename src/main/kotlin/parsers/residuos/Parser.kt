package parsers.residuos

import java.io.InputStream
import java.io.OutputStream

interface Parser<T> {
    /**
     * Read from an input stream and return a sequence of objects
     * *NOTE* It's the caller responsibility to close the input stream
     */
    fun parse(input: InputStream): Sequence<T>

    /**
     * Read from a sequence of objects and write to an output stream
     * *NOTE* It's the caller responsibility to close the output stream
     */
    fun unParse(input: Sequence<T>, outputStream: OutputStream)
}