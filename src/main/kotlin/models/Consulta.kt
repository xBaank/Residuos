package models

import dto.ContenedorDto
import dto.ResiduoDto

data class Consulta(
    val contenedores: Sequence<ContenedorDto>,
    val residuos: Sequence<ResiduoDto>,
)