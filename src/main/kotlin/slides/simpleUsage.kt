package ws.slides

import kotlinx.css.*
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
                )
    
                «opn«val db = DB.default.open("mydb")»
                
                «put«val user = User(UUID.randomUUID(),
                    "Salomon", "BRYS", Address("near Paris"))
                val key = db.put(user)»

                «get«val key = db.newKey<User>("...")
                val user = db[key]»

                «fin«val allBrys = db.find<User>()
                        .byIndex("lastName", "BRYS")
                        .models().toList()»
            """.trimIndent()
    ) {
        "code" {
//            overflow = Overflow.hidden
//            transition(::height, 300.ms)
//            val lines = when(props.state) {
//                0 -> 6
//                1 -> 8
//                2 -> 12
//                3 -> 15
//                else -> 19
//            }
//            height = (lines * 1.153).em
        }

        "span.c-marker" {
            opacity = 1.0
            transition(::opacity, 300.ms)
            +"c-opn" { if (props.state < 1) opacity = 0.0 }
            +"c-put" { if (props.state < 2) opacity = 0.0 }
            +"c-get" { if (props.state < 3) opacity = 0.0 }
            +"c-fin" { if (props.state < 4) opacity = 0.0 }
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
