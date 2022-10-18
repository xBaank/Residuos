package exporting.html

import dto.*
import exporting.html.Html.distritoCard
import exporting.html.Html.titleInfo
import extensions.exportToHtml
import extensions.toHtmlFormatted
import formats.IHtmlExporter
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Consulta
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

            distritoCard(distritoGroup.key.distrito) { html }
        }
    }

    //Solo Dios sabe que hay que hacer aqui
    private fun DIV.consulta2(list: DataFrame<ContenedorDto>) {
        val distritos = list.groupBy { distrito }

        distritos.forEach { distritoGroup ->
            val html = distritoGroup.group.groupBy { tipoContenedor }.aggregate { tipoContenedorGroup ->
                tipoContenedorGroup.mean { cantidadContenedores } into "media contenedores"
            }.toHtmlFormatted()

            distritoCard(distritoGroup.key.distrito) { html }
        }
    }

    private fun DIV.consulta3(contenedores: DataFrame<ContenedorDto>) {
        //Agrupamos por distrito, mapeamos cada distrito a su suma de contenedores y luego los añadimos la cantidad de contenedores que tenga
        val data = contenedores.groupBy { distrito }
            .aggregate { it.sumOf { cantidadContenedores } into "cantidad" }
            .toMap()


        val p = letsPlot(data) +
                geomBar(stat = Stat.identity, color = "dark_green", alpha = .3) { x = "Distrito"; y = "cantidad" } +
                ggtitle("Gráfico con el total de contenedores por distrito")

        distritoCard { p.exportToHtml() }
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

            distritoCard(distritoGroup.key.nombreDistrito) { html }
        }
    }

    private fun DIV.consulta5(residuos: DataFrame<ResiduoDto>) {
        val distritos = residuos
            .groupBy { nombreDistrito }

        distritos.forEach { distritoGroup ->
            val data = distritoGroup.group
                .groupBy { mes }
                .aggregate {
                    meanOf { toneladas } into "media"
                }.toMap()


            val p = letsPlot(data) +
                    geomBar(stat = Stat.identity, color = "dark_green", alpha = .3) {
                        x = "Meses"; y = "media"
                    } +
                    ggtitle("Gráfico de media de toneladas mensuales de recogida de basura en ${distritoGroup.key.nombreDistrito}")
            distritoCard(distritoGroup.key.nombreDistrito) { p.exportToHtml() }
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

        distritoCard(distritoGrouped.key.nombreDistrito) { html }
    }
}

private fun DIV.consulta7(residuosDf: DataFrame<ResiduoDto>) {
    val distritos = residuosDf.groupBy { nombreDistrito }

    distritos.forEach { distritoGrouped ->
        val html = distritoGrouped.group.groupBy { ano }.aggregate {
            sumOf { toneladas } into "suma"
        }.toHtmlFormatted()

        distritoCard(distritoGrouped.key.nombreDistrito) { html }
    }
}

private fun DIV.consulta8(residuosDf: DataFrame<ResiduoDto>) {
    val distritos = residuosDf.groupBy { nombreDistrito }

    distritos.forEach { distritoGrouped ->
        val html = distritoGrouped.group.groupBy { residuo }.aggregate {
            sumOf { toneladas } into "suma"
        }.toHtmlFormatted()

        distritoCard(distritoGrouped.key.nombreDistrito) { html }
    }
}










