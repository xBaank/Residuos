package extensions

import jetbrains.datalore.plot.PlotHtmlExport
import jetbrains.datalore.plot.PlotHtmlHelper.scriptUrl
import org.jetbrains.letsPlot.export.VersionChecker
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.intern.toSpec

fun Plot.exportToHtml() =
    PlotHtmlExport.buildHtmlFromRawSpecs(toSpec(), scriptUrl(VersionChecker.letsPlotJsVersion), true)