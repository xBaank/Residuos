package exporting.html

import dto.*
import exceptions.ExportException
import exporting.html.Html.distritoCard
import exporting.html.Html.titleInfo
import extensions.exportToHtml
import extensions.toHtmlFormatted
import formats.IHtmlExporter
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Consulta
import org.apache.commons.lang3.StringUtils
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import java.io.OutputStream
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class HtmlDistritoExporter(private var distritoNombre: String) : IHtmlExporter<Consulta> {

    init {
        distritoNombre = distritoNombre.clear
    }

    override fun export(input: Consulta, outputStream: OutputStream) {
        val residuosDf = input.residuos.filter { it.nombreDistrito.clear == distritoNombre }.toList().toDataFrame()
        val contenedoresDf = input.contenedores.filter { it.distrito.clear == distritoNombre }.toList().toDataFrame()
        if (residuosDf.isEmpty() || contenedoresDf.isEmpty()) {
            throw ExportException("El distrito $distritoNombre no existe en la consulta")
        }
        val start = Instant.now()
        writeHtml(outputStream, contenedoresDf, residuosDf, start)
    }

    private fun writeHtml(
        outputStream: OutputStream,
        contenedoresDf: DataFrame<ContenedorDto>,
        residuosDf: DataFrame<ResiduoDto>,
        start: Instant?,
    ) =
        outputStream.bufferedWriter().appendHTML().html {
            head {
                title { +"Resumen de recogidas de basura y reciclaje de Madrid" }
                link {
                    href = " \thttps://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"; rel =
                    "stylesheet"; attributes["crossorigin"] = "anonymous"
                }
            }
            body {

                nav("navbar navbar-dark bg-primary") {
                    div("container-fluid") {
                        span("navbar-brand mb-0 h1") { +"Resumen de recogidas de basura y reciclaje de Madrid" }
                        span("navbar-brand mb-0 h1") {
                            +"Fecha de consulta: ${
                                LocalDateTime.now().format(ISO_LOCAL_DATE_TIME)
                            }"
                        }
                        span("navbar-brand mb-0 h1") { +"Autores: Roberto Blázquez y Fran Toribio" }
                    }
                }

                div("container-fluid d-flex flex-column align-items-center text-center justify-content-center") {
                    titleInfo("Número de contenedores de cada tipo que hay en este distrito")
                    consulta1(contenedoresDf)
                    titleInfo("Total de toneladas recogidas por residuo en este distrito")
                    consulta2(residuosDf)
                    titleInfo("Gráfico con el total de toneladas por residuo en este distrito")
                    consulta3(residuosDf)
                    titleInfo("Máximo, mínimo , media y desviación por mes por residuo en dicho distrito")
                    consulta4(residuosDf)
                    titleInfo("Gráfica del máximo, mínimo y media por meses en dicho distrito")
                    consulta5(residuosDf)
                }


                p { +"Tiempo de ejecución: ${Duration.between(Instant.now(), start)}" }

                script {
                    src = " https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
                    attributes["crossorigin"] = "anonymous"
                }
            }
        }.flush()

    private fun DIV.consulta1(list: DataFrame<ContenedorDto>) {
        val html = list.groupBy { tipoContenedor }.aggregate { tipoContenedorGroup ->
            tipoContenedorGroup.sumOf { cantidadContenedores } into "cantidad contenedores"
        }.toHtmlFormatted()

        distritoCard(distritoNombre) { html }
    }

    //Solo Dios sabe que hay que hacer aqui
    private fun DIV.consulta2(list: DataFrame<ResiduoDto>) {

        val html = list.groupBy { residuo }.aggregate { tipoContenedorGroup ->
            tipoContenedorGroup.sumOf { toneladas } into "total toneladas"
        }.toHtmlFormatted()

        distritoCard(distritoNombre) { html }
    }

    private fun DIV.consulta3(list: DataFrame<ResiduoDto>) {
        //Agrupamos por distrito, mapeamos cada distrito a su suma de contenedores y luego los añadimos la cantidad de contenedores que tenga
        val distritosToToneladas = list.groupBy { residuo }.aggregate {
            sum { toneladas } into "total toneladas"
        }.toMap()

        val p = letsPlot(distritosToToneladas) +
                geomBar(stat = Stat.identity, color = "dark_green", alpha = .3) {
                    x = "Residuo"; y = "total toneladas"
                } +
                ggtitle("Gráfico de suma de toneladas por residuo en $distritoNombre")

        distritoCard(distritoNombre) { p.exportToHtml() }
    }

    private fun DIV.consulta4(list: DataFrame<ResiduoDto>) {
        list.groupBy { mes }.forEach {
            val html = it.group.groupBy { residuo }.aggregate {
                mean { toneladas } into "media"
                min { toneladas } into "minimo"
                max { toneladas } into "maximo"
                std { toneladas } into "desviacion"
            }.toHtmlFormatted()

            distritoCard("$distritoNombre ${it.key.mes.uppercase()}") { html }
        }
    }

    private fun DIV.consulta5(list: DataFrame<ResiduoDto>) {
        val distritosToToneladas = list.groupBy { mes }.aggregate {
            mean { toneladas } into "media"
            max { toneladas } into "maximo"
            min { toneladas } into "minimo"
        }.toMap()

        val p = letsPlot(distritosToToneladas) +
                geomBar(stat = Stat.identity, color = "dark_green", alpha = .3) { x = "Mes"; y = "maximo" } +
                ggtitle("Gráfico de maximo de toneladas mensuales de recogida de basura en $distritoNombre")

        distritoCard(distritoNombre) { p.exportToHtml() }

        val p2 = letsPlot(distritosToToneladas) +
                geomBar(stat = Stat.identity, color = "dark_green", alpha = .3) { x = "Mes"; y = "minimo" } +
                ggtitle("Gráfico de minimo de toneladas mensuales de recogida de basura en $distritoNombre")

        distritoCard(distritoNombre) { p2.exportToHtml() }


        val p3 = letsPlot(distritosToToneladas) +
                geomBar(stat = Stat.identity, color = "dark_green", alpha = .3) { x = "Mes"; y = "media" } +
                ggtitle("Gráfico de media de toneladas mensuales de recogida de basura en $distritoNombre")

        distritoCard(distritoNombre) { p3.exportToHtml() }
    }

    private val String.clear get() = StringUtils.stripAccents(this).uppercase()
}










