package controllers

import aliases.Contenedores
import aliases.IController
import aliases.Residuos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import readers.IFileReader
import writers.IFileWriter

/**
 * Controlador para la opcion parser
 */
class ParserController(
    private val residuosWriter: IFileWriter<Residuos>,
    private val contenedoresWriter: IFileWriter<Contenedores>,
    private val residuosReader: IFileReader<Residuos>,
    private val contenedoresReader: IFileReader<Contenedores>,
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