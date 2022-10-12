package parsers.formats

import parsers.FullParser

interface XmlParser<T> : FullParser<T> {
    override val extension: String
        get() = ".xml"
}