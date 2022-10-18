package exporting.html

import kotlinx.html.*

object Html {
    fun HtmlBlockTag.titleInfo(title: String) {
        div("alert alert-primary mt-5") {
            attributes["role"] = "alert"
            h4("alert-heading") { +title }
        }
    }

    fun DIV.distritoCard(header: String = "", body: () -> String) {
        div("card card border-info m-5 w-50") {
            div("card-header") { +header }
            div("card-body mx-auto") { unsafe { +body() } }
        }
    }
}