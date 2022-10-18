package controllers

import aliases.Contenedores
import aliases.IController
import aliases.Residuos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import models.Consulta
import readers.IFileReader
import writers.IFileWriter

/**
 * Controlador para la opcion resumen
 */
class ResumenController(
    private val writer: IFileWriter<Consulta>,
    private val residuosReader: IFileReader<Residuos>,
    private val contenedoresReader: IFileReader<Contenedores>,
) : IController {
    override suspend fun process() = withContext(Dispatchers.IO) {
        val residuosFuture =
            async { residuosReader.read() }
        val contenedoresFuture =
            async { contenedoresReader.read() }

        val consulta = Consulta(contenedoresFuture.await(), residuosFuture.await())

        writer.write(consulta)
    }

}