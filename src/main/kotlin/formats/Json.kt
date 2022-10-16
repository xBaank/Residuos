package formats

import exporting.IExporter
import importing.IImporter

interface IJsonImporter<T> : IImporter<T> {
    override val extension: String
        get() = ".json"
}

interface IJsonExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".json"
}