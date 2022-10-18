package args

import exceptions.ArgsException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ArgsImporterTest {

    @Test
    fun `should parse opcion parser`() {
        val options = ArgsParser("parser a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionParser)
        options as OpcionParser
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }

    @Test
    fun `should parser opcion resumen`() {
        val options = ArgsParser("resumen a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionResumen)
        options as OpcionResumen
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }

    @Test
    fun `should parse opcion resumen with distrito`() {
        val options = ArgsParser("resumen madrid a b".split(' ').toTypedArray()).parse()
        assert(options is OpcionResumen)
        options as OpcionResumen
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
        assert(options.distrito == "madrid")
    }

    @Test
    fun `should not parse empty parameters`() {
        assertThrows<ArgsException> { ArgsParser(emptyArray()).parse() }
    }

    @Test
    fun shouldNotParseUnknowOption() {
        assertThrows<ArgsException> { ArgsParser("asd".split(' ').toTypedArray()).parse() }
    }
}