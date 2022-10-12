package aliases

import dto.ContenedorDto
import dto.ResiduoDto
import parsers.Parser
import parsers.formats.CsvParser

typealias Contenedores = Sequence<ContenedorDto>
typealias Residuos = Sequence<ResiduoDto>

typealias SequenceParser<T> = Parser<Sequence<T>>
typealias CsvSequenceParser<T> = CsvParser<Sequence<T>>

