package importing

import java.io.InputStream

/**
 * Interfaz para importar datos desde un stream, puede ser un archivo o un recurso de red
 */
interface IImporter<out T> {
    val extension: String

    /**
     * *NOTE* Es responsabilidad del llamador cerrar el stream
     */
    fun import(input: InputStream): T


}