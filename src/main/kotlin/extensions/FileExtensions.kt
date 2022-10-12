package extensions

import java.io.File

val File.firstLine: String? get() = inputStream().bufferedReader().useLines { it.firstOrNull()?.replace("\uFEFF", "") }