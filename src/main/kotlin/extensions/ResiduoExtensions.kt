package extensions

import dto.ResiduoDto
import models.Residuo

fun ResiduoDto.to(): Residuo {
    return Residuo()

}

fun Residuo.to(): ResiduoDto {
    return ResiduoDto()
}

