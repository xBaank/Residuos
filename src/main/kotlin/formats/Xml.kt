package formats

import exporting.IExporter
import importing.IImporter

interface IXmlImporter<T> : IImporter<T> {
    override val extension: String
        get() = ".xml"
}

interface IXmlExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".xml"
}