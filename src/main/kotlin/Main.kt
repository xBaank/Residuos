import args.ArgsParser
import args.Opcion
import parsers.residuos.CsvParserResiduos
import readers.FileReader
import writers.ResiduosFileWriter

fun main(args: Array<String>) {
    val opcion = ArgsParser(args).parse()
    val residuosFileWriter = ResiduosFileWriter(opcion.directorioDestino)
    val fileReader = FileReader(opcion.directorioOrigen, CsvParserResiduos())

    when (opcion) {
        is Opcion.OpcionParser -> {
            val residuos = fileReader.read()
            residuosFileWriter.write(residuos)
        }

        is Opcion.OpcionResumen -> {
            TODO()
        }
    }
}