package extensions

import java.io.File


internal fun String.asArgs() = trim().split(" ").toTypedArray()

internal infix fun File.isSizeOf(file: File) = length() == file.length()
internal infix fun File.hasGreaterSizeThan(value: Long) = length() > value