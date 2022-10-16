package formats.residuos

import aliases.Residuos
import formats.ICsvExporter
import formats.ICsvImporter

private const val FIRSTLINE = "Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas"

interface ICsvExporterResiduos : ICsvExporter<Residuos> {
    override val firstLine: String
        get() = FIRSTLINE
}

interface ICsvImporterResiduos : ICsvImporter<Residuos> {
    override val firstLine: String
        get() = FIRSTLINE
}