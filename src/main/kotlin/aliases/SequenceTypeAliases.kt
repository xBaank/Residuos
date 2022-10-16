package aliases

import dto.ContenedorDto
import dto.ResiduoDto
import formats.ICsvImporter
import importing.IImporter

typealias Contenedores = Sequence<ContenedorDto>
typealias Residuos = Sequence<ResiduoDto>

typealias SequenceImporter<T> = IImporter<Sequence<T>>
typealias CsvSequenceImporter<T> = ICsvImporter<Sequence<T>>

