package extensions

import java.io.File

/**
 * retorna la primera linea de un archivo quitando el BOM
 */
val File.firstLine: String? get() = inputStream().bufferedReader().useLines { it.firstOrNull()?.replace("\uFEFF", "") }