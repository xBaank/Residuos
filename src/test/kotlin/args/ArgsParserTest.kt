package args

import org.junit.jupiter.api.Test

internal class ArgsParserTest {

    @Test
    fun parseOpcionParser() {
        val options = ArgsParser("parser a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionConfig.OpcionParser)
        options as OpcionConfig.OpcionParser
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }

    @Test
    fun parseOpcionResumen() {
        val options = ArgsParser("resumen madrid a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionConfig.OpcionResumen)
        options as OpcionConfig.OpcionResumen
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }
}