package extensions

import exceptions.ImportException
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.toHTML

/**
 * Filtra la primera linea de una secuencia de strings
 */
fun Sequence<String>.filterFirstLine(firstLine: String): Sequence<String> = sequence {
    var isFirstLine = true
    forEach {
        if (isFirstLine) {
            val header = it.replace("\uFEFF", "")
            if (firstLine != header)
                throw ImportException("El formato del csv no es correcto")
            isFirstLine = false
        }
        yield(it)
    }
}

/**
 * devuelve el argumento de un array de strings
 */
fun Array<String>.getArgument(argument: String) =
    firstOrNull { it.startsWith(argument) }?.replace(argument, "").takeIf { it?.trim() != "" }

/**
 * retorna un array de strings sin el argumento
 */
fun Array<String>.removeArguments(vararg arguments: String) =
    filter { !arguments.any { argument -> it.startsWith(argument) } }.toTypedArray()

fun <T> DataFrame<T>.toHtmlFormatted() = this.toHTML(includeInit = true) { "" }.toString()