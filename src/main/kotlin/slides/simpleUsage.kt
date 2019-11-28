package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import styled.StyledComponents.css
import styled.css
import styled.styledDiv
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos
import ws.kpres.sourceCode


private val infos = SlideInfos(
        stateCount = 7
)

fun PresentationBuilder.simpleUsage() = slide(infos) { props ->

    sourceCode(
            "kotlin",
            """
                data class User(
                    «ano«@Id »val uid: String,
                    val firstName: String,
                    «ano«@Indexed("lastName") »val lastName: String
                    val address: «key«Key<»Address«key«>»?
                )«opn«
    
                val db = DB.default.open("mydb")»«put«
                
                val user = User(UUID.randomUUID(),
                    "Salomon", "BRYS", Address("near Paris"))
                val key = db.put(user)»«get«

                val key = db.newKey<User>("...")
                val user = db[key]»«fin«

                val allBrys = db.find<User>()
                        .byIndex("lastName", "BRYS")
                        .models().toList()»
            """.trimIndent()
    ) {
        "code" {
            overflow = Overflow.hidden
        }

        "span.c-marker" {
            opacity = 1.0
            transition(::opacity, 300.ms)
            transition(::lineHeight, 300.ms)
            fun CSSBuilder.minState(min: Int) {
                opacity = if (props.state < min) 0.0 else 1.0
                lineHeight = LineHeight(if (props.state < min) "0" else "1.2")
            }
            +"c-opn" { minState(1) }
            +"c-put" { minState(2) }
            +"c-get" { minState(3) }
            +"c-fin" { minState(4) }
            +"c-ano" {
                fontSize = 1.em
                transition(::fontSize, 300.ms)
                if (props.state < 5) fontSize = 0.em
                fontWeight = FontWeight.w500
                universal { color = Color.white }
                verticalAlign = VerticalAlign.middle
            }
            +"c-key" {
                fontSize = 1.em
                transition(::fontSize, 300.ms)
                if (props.state < 6) fontSize = 0.em
                fontWeight = FontWeight.w500
                color = Color.white
                verticalAlign = VerticalAlign.middle
            }
        }
    }
}
