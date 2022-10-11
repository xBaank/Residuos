package parsers.html

import extensions.exportToHtml
import extensions.toContenedor
import extensions.toResiduo
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Consulta
import models.Contenedor
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.ggsize
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import parsers.UnParser
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

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
                h3 { +"Fecha: ${LocalDateTime.now().format(ISO_LOCAL_DATE_TIME)}" }
                h3 { +"Autores: Roberto Blázquez y Fran Toribio" }
                h1 { +"Número de contenedores de cada tipo que hay en cada distrito" }
                consulta1(contenedores)
                h1 { +"Media de contenedores de cada tipo que hay en cada distrito" }
                consulta2(contenedores)
                h1 { +"Gráfico con el total de contenedores por distrito" }
                consulta3(contenedores)
                h1 { +" Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito" }
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

    //Solo Dios sabe que hay que hacer aqui
    private fun BODY.consulta2(list: Sequence<Contenedor>) {
        list.groupBy {
            it.tipoContenedor
        }.map { distrito ->
            distrito.key to distrito.value.map { it.cantidadContenedores }.average()
        }.forEach {
            p { +"Distrito ${it.first}" }
            p { +"Media contenedores : ${it.second}" }
        }
    }

    private fun BODY.consulta3(list: Sequence<Contenedor>) = unsafe {
        //Agrupamos por distrito, mapeamos cada distrito a su suma de contenedores y luego los añadimos la cantidad de contenedores que tenga
        val distritos = list.groupBy { it.distrito }
            .map { distrito -> distrito.key to distrito.value.sumOf { it.cantidadContenedores } }
            .flatMap { pair ->
                val count = mutableListOf<String>()
                repeat(pair.second) { count.add(pair.first) }
                count
            }

        val data = mapOf(
            "Distritos" to distritos
        )
        var p = letsPlot(data)
        p += geomBar(color = "dark_green", alpha = .3) { x = "Distritos" } +
                ggsize(700, 350) +
                ggtitle("Gráfico con el total de contenedores por distrito")

        +p.exportToHtml()
    }
}