package extensions

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.toHTML

fun <T> DataFrame<T>.toHtmlFormatted() = this.toHTML(includeInit = true) { "" }.toString()