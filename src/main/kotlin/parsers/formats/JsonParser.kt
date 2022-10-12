package parsers.formats

import parsers.FullParser

interface JsonParser<T> : FullParser<T> {
    override val extension: String
        get() = ".json"
}