package parsers.formats

import parsers.FullParser

interface CsvParser<T> : FullParser<T> {
    val firstLine: String
    override val extension: String
        get() = ".csv"
}