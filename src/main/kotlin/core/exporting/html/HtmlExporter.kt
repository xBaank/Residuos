package core.exporting.html

import core.exporting.html.Html.titleInfo
import core.formats.IHtmlExporter
import dto.*
import extensions.exportToHtml
import extensions.toHtmlFormatted
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Consulta
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import java.io.OutputStream
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class HtmlExporter : IHtmlExporter<Consulta> {
    override fun export(input: Consulta, outputStream: OutputStream) {
        val residuosDf = input.residuos.toList().toDataFrame()
        val contenedoresDf = input.contenedores.toList().toDataFrame()
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
                    titleInfo("Número de contenedores de cada tipo que hay en cada distrito")
                    consulta1(contenedoresDf)
                    titleInfo("Media de contenedores de cada tipo que hay en cada distrito")
                    consulta2(contenedoresDf)
                    titleInfo("Gráfico con el total de contenedores por distrito")
                    consulta3(contenedoresDf)
                    titleInfo("Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito")
                    consulta4(residuosDf)
                    titleInfo("Gráfico de media de toneladas mensuales de recogida de basura por distrito")
                    consulta5(residuosDf)
                    titleInfo("Máximo, mínimo , media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito")
                    consulta6(residuosDf)
                    titleInfo("Suma de todo lo recogido en un año por distrito")
                    consulta7(residuosDf)
                    titleInfo("Por cada distrito obtener para cada tipo de residuo la cantidad recogida")
                    consulta8(residuosDf)
                }


                p { +"Tiempo de ejecución: ${Duration.between(Instant.now(), start)}" }

                script {
                    src = " https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
                    attributes["crossorigin"] = "anonymous"
                }
            }
        }.flush()

    private fun DIV.consulta1(list: DataFrame<ContenedorDto>) {
        val distritos = list.groupBy { distrito }

        distritos.forEach { distritoGroup ->
            val html = distritoGroup.group.groupBy { tipoContenedor }.aggregate { tipoContenedorGroup ->
                tipoContenedorGroup.sumOf { cantidadContenedores } into "cantidad contenedores"
            }.toHtmlFormatted()

            div("card card border-info m-5 w-50") {
                div("card-header") { +distritoGroup.key.distrito }
                div("card-body mx-auto") { unsafe { +html } }
            }
        }
    }

    //Solo Dios sabe que hay que hacer aqui
    private fun DIV.consulta2(list: DataFrame<ContenedorDto>) {
        val distritos = list.groupBy { distrito }

        distritos.forEach { distritoGroup ->
            val html = distritoGroup.group.groupBy { tipoContenedor }.aggregate { tipoContenedorGroup ->
                tipoContenedorGroup.mean { cantidadContenedores } into "media contenedores"
            }.toHtmlFormatted()

            div("card card border-info m-5 w-50") {
                div("card-header") { +distritoGroup.key.distrito }
                div("card-body mx-auto") { unsafe { +html } }
            }
        }
    }

    private fun DIV.consulta3(contenedores: DataFrame<ContenedorDto>) {
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
                ggtitle("Gráfico con el total de contenedores por distrito")

        unsafe { +p.exportToHtml() }
    }

    private fun DIV.consulta4(residuos: DataFrame<ResiduoDto>) {
        val distritos = residuos.groupBy { nombreDistrito }

        distritos.forEach { distritoGroup ->
            val html =
                distritoGroup.group
                    .groupBy { ano }
                    .aggregate {
                        meanOf { toneladas } into "media toneladas anuales"
                    }.toHtmlFormatted()

            div("card card border-info m-5 w-50") {
                div("card-header") { +distritoGroup.key.nombreDistrito }
                div("card-body mx-auto") { unsafe { +html } }
            }
        }
    }

    private fun DIV.consulta5(residuos: DataFrame<ResiduoDto>) {
        val distritos = residuos
            .groupBy { nombreDistrito }


        distritos.forEach { distrito ->
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
                    ggtitle("Gráfico de media de toneladas mensuales de recogida de basura en ${distrito.key.nombreDistrito}")

            div("card card border-info m-5 w-50") {
                div("card-header") { +distrito.key.nombreDistrito }
                div("card-body mx-auto") { unsafe { +p.exportToHtml() } }
            }
        }
    }

    private fun DIV.consulta6(residuos: DataFrame<ResiduoDto>) {
        val distritos = residuos.groupBy { it.nombreDistrito }

        distritos.forEach { distritoGrouped ->
            val html = distritoGrouped.group.groupBy { residuo; ano }.aggregate {
                max { toneladas } into "max"
                min { toneladas } into "min"
                mean { toneladas } into "media"
                std { toneladas } into "desviacion"
            }.toHtmlFormatted()

            div("card card border-info m-5 w-50") {
                div("card-header") { +distritoGrouped.key.nombreDistrito }
                div("card-body mx-auto") { unsafe { +html } }
            }
        }
    }

    private fun DIV.consulta7(residuosDf: DataFrame<ResiduoDto>) {
        val distritos = residuosDf.groupBy { nombreDistrito }

        distritos.forEach { distritoGrouped ->
            val html = distritoGrouped.group.groupBy { ano }.aggregate {
                sumOf { toneladas } into "suma"
            }.toHtmlFormatted()

            div("card card border-info m-5 w-50") {
                div("card-header") { +distritoGrouped.key.nombreDistrito }
                div("card-body mx-auto") { unsafe { +html } }
            }
        }
    }

    private fun DIV.consulta8(residuosDf: DataFrame<ResiduoDto>) {
        val distritos = residuosDf.groupBy { nombreDistrito }

        distritos.forEach { distritoGrouped ->
            val html = distritoGrouped.group.groupBy { residuo }.aggregate {
                sumOf { toneladas } into "suma"
            }.toHtmlFormatted()

            div("card card border-info m-5 w-50") {
                div("card-header") { +distritoGrouped.key.nombreDistrito }
                div("card-body mx-auto") { unsafe { +html } }
            }
        }
    }
}










