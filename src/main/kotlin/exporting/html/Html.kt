package exporting.html

import kotlinx.html.HtmlBlockTag
import kotlinx.html.div
import kotlinx.html.h4

object Html {
    fun HtmlBlockTag.titleInfo(title: String) {
        div("alert alert-primary mt-5") {
            attributes["role"] = "alert"
            h4("alert-heading") { +title }
        }
    }
}