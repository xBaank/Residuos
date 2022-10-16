package formats

import exporting.IExporter

interface IHtmlExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".html"
}