package extensions

import dto.ContenedorDto
import models.Contenedor
import models.TipoContenedor

fun Contenedor.toContenedorDto(): ContenedorDto {
    return ContenedorDto(
        codIntSitu,
        tipoContenedor.tipo,
        modelo,
        descripModelo,
        cantidadContenedores,
        lote,
        distrito,
        barrio,
        tipoVia,
        nombreVia,
        numVia,
        coordenadaX,
        coordenadaY,
        longitud,
        latitud,
        direccion
    )
}

fun ContenedorDto.toContenedor(): Contenedor {
    return Contenedor(
        codIntSitu,
        TipoContenedor.values().firstOrNull { it.tipo == tipoContenedor.uppercase() }
            ?: throw IllegalArgumentException("El tipo $tipoContenedor de contenedor no es válido"),
        modelo,
        descripModelo,
        cantidadContenedores,
        lote,
        distrito,
        barrio,
        tipoVia,
        nombreVia,
        numVia,
        coordenadaX,
        coordenadaY,
        longitud,
        latitud,
        direccion
    )
}

fun Sequence<ContenedorDto>.toContenedor(): Sequence<Contenedor> = map {
    it.toContenedor()
}

fun Sequence<Contenedor>.toContenedorDto(): Sequence<ContenedorDto> = map {
    it.toContenedorDto()
}