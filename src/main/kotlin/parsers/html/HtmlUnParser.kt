package parsers.html

import dto.*
import extensions.exportToHtml
import extensions.toHtmlFormatted
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Consulta
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.ggsize
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import parsers.UnParser
import java.io.OutputStream
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class HtmlUnParser : UnParser<Consulta> {
    override val extension: String
        get() = ".html"

    override fun unParse(input: Consulta, outputStream: OutputStream) {
        val residuosDf = input.residuos.toList().toDataFrame()
        val contenedoresDf = input.contenedores.toList().toDataFrame()
        val start = Instant.now()

        outputStream.bufferedWriter().appendHTML().html {
            head {
                title { +"Resumen de recogidas de basura y reciclaje de Madrid" }
            }
            body {
                h1 { +"Resumen de recogidas de basura y reciclaje de Madrid" }
                h3 { +"Fecha: ${LocalDateTime.now().format(ISO_LOCAL_DATE_TIME)}" }
                h3 { +"Autores: Roberto Blázquez y Fran Toribio" }
                h1 { +"Número de contenedores de cada tipo que hay en cada distrito" }
                consulta1(contenedoresDf)
                h1 { +"Media de contenedores de cada tipo que hay en cada distrito" }
                consulta2(contenedoresDf)
                h1 { +"Gráfico con el total de contenedores por distrito" }
                consulta3(contenedoresDf)
                h1 { +" Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito" }
                consulta4(residuosDf)
                h1 { +"Gráfico de media de toneladas mensuales de recogida de basura por distrito" }
                consulta5(residuosDf)
                h1 { +"Máximo, mínimo , media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito" }
                consulta6(residuosDf)
                h1 { +"- Suma de todo lo recogido en un año por distrito" }
                consulta7(residuosDf)
                h1 { +"Por cada distrito obtener para cada tipo de residuo la cantidad recogida" }
                consulta8(residuosDf)

                p { +"Tiempo de ejecución: ${Duration.between(Instant.now(), start)}" }
            }
        }.flush()
    }

    private fun BODY.consulta1(list: DataFrame<ContenedorDto>) {
        val distritos = list.groupBy { distrito }

        distritos.forEach { distritoGroup ->
            h2 { +distritoGroup.key.distrito }
            val html = distritoGroup.group.groupBy { tipoContenedor }.aggregate { tipoContenedorGroup ->
                tipoContenedorGroup.sumOf { cantidadContenedores } into "cantidad contenedores"
            }.toHtmlFormatted()

            unsafe { +html }
        }
    }

    //Solo Dios sabe que hay que hacer aqui
    private fun BODY.consulta2(list: DataFrame<ContenedorDto>) {
        val distritos = list.groupBy { distrito }

        distritos.forEach { distritoGroup ->
            h2 { +distritoGroup.key.distrito }
            val html = distritoGroup.group.groupBy { tipoContenedor }.aggregate { tipoContenedorGroup ->
                tipoContenedorGroup.mean { cantidadContenedores } into "media contenedores"
            }.toHtmlFormatted()

            unsafe { +html }
        }
    }

    private fun BODY.consulta3(contenedores: DataFrame<ContenedorDto>) {
        //Agrupamos por distrito, mapeamos cada distrito a su suma de contenedores y luego los añadimos la cantidad de contenedores que tenga
        val distritos = contenedores.groupBy { distrito }

        val cantidadContenedores = distritos.map { distritoGroup ->
            val list = mutableListOf<String>()
            repeat(distritoGroup.group.sumOf { cantidadContenedores }) {
                list.add(distritoGroup.key.distrito)
            }
            list
        }.flatten()

        val data = mapOf(
            "Distritos" to cantidadContenedores
        )
        val p = letsPlot(data) +
                geomBar(color = "dark_green", alpha = .3) { x = "Distritos" } +
                ggsize(700, 350) +
                ggtitle("Gráfico con el total de contenedores por distrito")

        unsafe { +p.exportToHtml() }
    }

    private fun BODY.consulta4(residuos: DataFrame<ResiduoDto>) {
        val distritos = residuos.groupBy { nombreDistrito }

        distritos.forEach { distritoGroup ->
            h2 { +distritoGroup.key.nombreDistrito }
            val html =
                distritoGroup.group
                    .groupBy { ano }
                    .aggregate {
                        meanOf { toneladas } into "media toneladas anuales"
                    }.toHtmlFormatted()

            unsafe { +html }
        }
    }

    private fun BODY.consulta5(residuos: DataFrame<ResiduoDto>) {
        val distritos = residuos
            .groupBy { nombreDistrito }


        distritos.forEach { distrito ->
            h2 { +"Distrito ${distrito.key.nombreDistrito}" }

            val distritosToToneladas = distrito.group.groupBy { mes }.map { fechaGroup ->
                val list = mutableListOf<String>()
                repeat(fechaGroup.group.meanOf { toneladas }.toInt()) {
                    list.add(fechaGroup.key.mes)
                }
                list
            }.flatten()

            val data = mapOf(
                "Meses" to distritosToToneladas
            )

            val p = letsPlot(data) +
                    geomBar(color = "dark_green", alpha = .3) { x = "Meses" } +
                    ggsize(700, 350) +
                    ggtitle("Gráfico de media de toneladas mensuales de recogida de basura en ${distrito.key.nombreDistrito}")

            unsafe { +p.exportToHtml() }
        }
    }

    private fun BODY.consulta6(residuos: DataFrame<ResiduoDto>) {
        val distritos = residuos.groupBy { it.nombreDistrito }

        distritos.forEach { distritoGrouped ->
            h2 { +distritoGrouped.key.nombreDistrito }
            val html = distritoGrouped.group.groupBy { residuo; ano }.aggregate {
                max { toneladas } into "max"
                min { toneladas } into "min"
                mean { toneladas } into "media"
                std { toneladas } into "desviacion"
            }.toHtmlFormatted()

            unsafe { +html }
        }
    }

    private fun BODY.consulta7(residuosDf: DataFrame<ResiduoDto>) {
        val distritos = residuosDf.groupBy { nombreDistrito }

        distritos.forEach { distritoGrouped ->
            h2 { +distritoGrouped.key.nombreDistrito }
            val html = distritoGrouped.group.groupBy { ano }.aggregate {
                sumOf { toneladas } into "suma"
            }.toHtmlFormatted()

            unsafe { +html }
        }
    }

    private fun BODY.consulta8(residuosDf: DataFrame<ResiduoDto>) {
        val distritos = residuosDf.groupBy { nombreDistrito }

        distritos.forEach { distritoGrouped ->
            h2 { +distritoGrouped.key.nombreDistrito }
            val html = distritoGrouped.group.groupBy { residuo }.aggregate {
                sumOf { toneladas } into "suma"
            }.toHtmlFormatted()

            unsafe { +html }
        }

    }
}










