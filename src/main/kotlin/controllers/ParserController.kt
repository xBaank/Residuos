package controllers

import aliases.Contenedores
import aliases.Residuos
import dto.ContenedorDto
import dto.ResiduoDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import readers.IReader
import writers.IWriter

class ParserController(
    private val residuosWriter: IWriter<Residuos>,
    private val contenedoresWriter: IWriter<Contenedores>,
    private val residuosReader: IReader<ResiduoDto>,
    private val contenedoresReader: IReader<ContenedorDto>
) : IController {

    override suspend fun process(): Unit = coroutineScope {
        val residuosFuture =
            async { residuosReader.read() }
        val contenedoresFuture =
            async { contenedoresReader.read() }

        //write async
        launch { residuosWriter.write(residuosFuture.await()) }
        launch { contenedoresWriter.write(contenedoresFuture.await()) }
    }
}