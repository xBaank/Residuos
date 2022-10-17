package args

import exceptions.ArgsException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ArgsImporterTest {

    @Test
    fun shouldParseOpcionParser() {
        val options = ArgsParser("parser a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionParser)
        options as OpcionParser
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }

    @Test
    fun shouldParseOpcionResumen() {
        val options = ArgsParser("resumen a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionResumen)
        options as OpcionResumen
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }

    @Test
    fun shouldParseOpcionResumenWithDistrito() {
        val options = ArgsParser("resumen madrid a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionResumen)
        options as OpcionResumen
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
        assert(options.distrito == "madrid")
    }

    @Test
    fun shouldNotParseEmptyArgs() {
        assertThrows<ArgsException> { ArgsParser(emptyArray()).parse() }
    }

    @Test
    fun shouldNotParseUnknowOption() {
        assertThrows<ArgsException> { ArgsParser("asd".split(' ').toTypedArray()).parse() }
    }
}