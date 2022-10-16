package exporting

import java.io.OutputStream

interface IExporter<in T> {
    val extension: String

    /**
     * Read from object and write to an output stream
     * *NOTE* It's the caller responsibility to close the output stream
     */
    fun export(input: T, outputStream: OutputStream)
}