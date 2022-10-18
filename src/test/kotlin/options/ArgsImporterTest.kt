package options

import controllers.ArgsController
import exceptions.ArgsException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ArgsImporterTest {

    @Test
    fun `should parse opcion parser`() = runBlocking {
        val options = ArgsController("parser a b".split(' ').toTypedArray()).process()
        assert(options is OpcionParser)
        options as OpcionParser
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }

    @Test
    fun `should parser opcion resumen`() = runBlocking {
        val options = ArgsController("resumen a b".split(' ').toTypedArray()).process()
        assert(options is OpcionResumen)
        options as OpcionResumen
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
    }

    @Test
    fun `should parse opcion resumen with distrito`() = runBlocking {
        val options = ArgsController("resumen madrid a b".split(' ').toTypedArray()).process()
        assert(options is OpcionResumen)
        options as OpcionResumen
        assert(options.directorioOrigen == "a")
        assert(options.directorioDestino == "b")
        assert(options.distrito == "madrid")
    }

    @Test
    fun `should not parse empty parameters`() {
        assertThrows<ArgsException> { runBlocking { ArgsController(emptyArray()).process() } }
    }

    @Test
    fun shouldNotParseUnknowOption() {
        assertThrows<ArgsException> { runBlocking { ArgsController("asd".split(' ').toTypedArray()).process() } }
    }
}