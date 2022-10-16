package extensions

import exceptions.ImportException

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