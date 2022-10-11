package parsers.html

import extensions.toContenedor
import extensions.toResiduo
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Consulta
import models.Contenedor
import parsers.UnParser
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HtmlUnParser : UnParser<Consulta> {
    override val extension: String
        get() = ".html"

    override fun unParse(input: Consulta, outputStream: OutputStream) {
        val contenedores = input.contenedores.toContenedor()
        val residuos = input.residuos.toResiduo()

        outputStream.bufferedWriter().appendHTML().html {
            head {
                title { +"Resumen de recogidas de basura y reciclaje de Madrid" }
            }
            body {
                h1 { +"Resumen de recogidas de basura y reciclaje de Madrid" }
                h3 { +"Fecha: ${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}" }
                h3 { +"Autores: Roberto Blázquez y Fran Toribio" }
                h1 { +"Número de contenedores de cada tipo que hay en cada distrito" }
                consulta1(contenedores)
                h1 { +"Media de contenedores de cada tipo que " }
            }
        }.flush()
    }

    private fun BODY.consulta1(list: Sequence<Contenedor>) {
        list.groupBy {
            it.distrito
        }.forEach {
            p { +"Distrito ${it.key}" }
            ul {
                it.value.groupBy {
                    it.tipoContenedor
                }.forEach {
                    li { +"${it.key}: ${it.value.size}" }
                }
            }
        }
    }

    private fun BODY.consulta2(list: Sequence<Contenedor>) {
        list.groupBy {
            it.distrito
        }.forEach {
            p { +"Distrito ${it.key}" }
            ul {
                it.value.groupBy {
                    it.tipoContenedor
                }.forEach {
                    li { +"${it.key}: ${it.value.size}" }
                }
            }
        }
    }
}