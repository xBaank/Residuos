package exporting

import java.io.OutputStream

/**
 * Interfaz para exportar datos a un stream, puede ser un archivo o un recurso de red
 */
interface IExporter<in T> {
    val extension: String

    /**
     * *NOTE* Es responsabilidad del llamador cerrar el stream
     */
    fun export(input: T, outputStream: OutputStream)
}