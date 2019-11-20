package ws.slides

import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.css.div
import ws.comp.logo
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos


private val infos = SlideInfos(
        bodyStyle = {
            div {
                +"darker" {
                    backgroundColor = Color("rgba(0, 0, 0, 0)")
                }
            }
        }
)

fun PresentationBuilder.kodeinKoders() = slide(infos) {
    logo(division = "Koders", href = "https://kodein.net", zoom = 1.0 ) {
        +"painless technology"
    }
}

