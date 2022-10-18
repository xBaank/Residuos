package controllers

import aliases.Contenedores
import aliases.Residuos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import readers.IReader
import writers.IWriter

class ParserController(
    private val residuosWriter: IWriter<Residuos>,
    private val contenedoresWriter: IWriter<Contenedores>,
    private val residuosReader: IReader<Residuos>,
    private val contenedoresReader: IReader<Contenedores>,
) : IController {

    override suspend fun process(): Unit = withContext(Dispatchers.IO) {
        val residuosFuture =
            async { residuosReader.read() }
        val contenedoresFuture =
            async { contenedoresReader.read() }

        //write async
        val residuosWriterFuture = async { residuosWriter.write(residuosFuture.await()) }
        val contenedoresWriterFuture = async { contenedoresWriter.write(contenedoresFuture.await()) }

        awaitAll(residuosWriterFuture, contenedoresWriterFuture)
    }
}