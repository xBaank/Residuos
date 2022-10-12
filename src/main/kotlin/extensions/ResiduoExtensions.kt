package extensions

import dto.ResiduoDto
import models.Residuo
import java.time.LocalDate
import java.time.Month
import java.util.*

fun ResiduoDto.toResiduo(): Residuo {
    val fecha = LocalDate.of(
        ano.toInt(),
        mes.parse(),
        1
    )
    return Residuo(fecha, lote, residuo, distrito, nombreDistrito, toneladas)

}

fun Residuo.toResiduoDto(): ResiduoDto {
    return ResiduoDto(
        fecha.year.toString(),
        fecha.month.parse(),
        lote,
        residuo,
        distrito,
        nombreDistrito,
        toneladas
    )
}

fun Sequence<ResiduoDto>.toResiduo(): Sequence<Residuo> = map {
    it.toResiduo()
}

fun Sequence<Residuo>.toResiduoDto(): Sequence<ResiduoDto> = map {
    it.toResiduoDto()
}


private fun String.parse(): Month {
    return when (this.lowercase(Locale.getDefault())) {
        "enero" -> Month.JANUARY
        "febrero" -> Month.FEBRUARY
        "marzo" -> Month.MARCH
        "abril" -> Month.APRIL
        "mayo" -> Month.MAY
        "junio" -> Month.JUNE
        "julio" -> Month.JULY
        "agosto" -> Month.AUGUST
        "septiembre" -> Month.SEPTEMBER
        "octubre" -> Month.OCTOBER
        "noviembre" -> Month.NOVEMBER
        "diciembre" -> Month.DECEMBER
        else -> throw IllegalArgumentException("El mes no es válido")
    }
}

//reverse parse
private fun Month.parse(): String {
    return when (this) {
        Month.JANUARY -> "enero"
        Month.FEBRUARY -> "febrero"
        Month.MARCH -> "marzo"
        Month.APRIL -> "abril"
        Month.MAY -> "mayo"
        Month.JUNE -> "junio"
        Month.JULY -> "julio"
        Month.AUGUST -> "agosto"
        Month.SEPTEMBER -> "septiembre"
        Month.OCTOBER -> "octubre"
        Month.NOVEMBER -> "noviembre"
        Month.DECEMBER -> "diciembre"
        else -> throw IllegalArgumentException("El mes no es válido")
    }
}
