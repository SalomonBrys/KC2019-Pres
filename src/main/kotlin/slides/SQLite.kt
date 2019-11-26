package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.KeyframesBuilder
import kotlinx.css.properties.ms
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import react.*
import react.dom.div
import react.dom.h1
import react.dom.li
import react.dom.span
import styled.*
import ws.kpres.PresentationBuilder
import ws.kpres.SlideContentProps
import ws.kpres.SlideInfos
import ws.kpres.sourceCode
import ws.utils.getValue


private val infos = SlideInfos(
        stateCount = 8
)

private fun RBuilder.entry(state: Int, thisState: Int, name: String, lang: String, code: String) {
    li {
        styledSpan {
            css {
                specific {
                    if (state < thisState) opacity = 0.0
                    else if (state in (thisState + 1)..5) opacity = 0.25

                    position = Position.relative

                    if (thisState != 0 && state >= 7) {
                        opacity = 0.5
                        after {
                            content = QuotedString(" ")
                            position = Position.absolute
                            top = 45.pct
                            left = 0.pct
                            width = 100.pct
                            height = 0.06.em
                            backgroundColor = Color.white
                            animation(1.s) {
                                0 { width = 0.pct }
                                100 { width = 100.pct }
                            }
                        }
                    }
                }
            }
            +name
        }

        div("code") {
            sourceCode(
                    lang,
                    code
            )
        }
    }

}

private val SQLiteSlide by functionalComponent<SlideContentProps> { props ->
    val ul = useRef<HTMLElement?>(null)

    useEffect(listOf(props.state)) {
        val codes = ul.current!!.querySelectorAll("div.code").asList()
        codes.forEach { (it as HTMLElement).style.height = "0" }
        if (props.state < codes.size) {
            val code = codes[props.state] as HTMLElement
            code.style.height = "calc(${(code.firstChild!! as HTMLElement).clientHeight}px + 1em)"
        }
    }

    h1 {
        +" SQLite"
    }

    styledUl {
        ref = ul
        css {
            margin(0.em)
            display = Display.flex
            flexDirection = FlexDirection.column
            alignSelf = Align.stretch
            "li" {
                "span" {
                    opacity = 1.0
                    transition(::opacity, 300.ms)
                }
                "pre" {
                    margin(0.5.em, 2.em)
                    transition(::height, 300.ms)
                }
            }
            "div.code" {
                height = 0.px
                overflow = Overflow.hidden
                if (props.shouldAnim) {
                    transition(::height, 300.ms)
                }
            }
        }

        entry(props.state, 0, "Models", "kotlin",
                """
                    data class User(
                        val id: Int,
                        val firstName: String,
                        val lastName: String
                        val address: Address?
                    )
                """.trimIndent()
        )

        entry(props.state, 1, "Structure", "SQL",
                """ 
                    CREATE TABLE User(
                        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                        firstName TEXT NOT NULL,
                        lastName TEXT NOT NULL,
                        addressId INT
                    );
                """.trimIndent()
        )

        entry(props.state, 2, "Creation", "kotlin",
                """
                    fun getDB() =
                        if (!db_exists("mydb.sql"))
                            create_db()
                                .also { initiate_schema(it) }
                        else
                            open_db("mydb.sql")
                """.trimIndent()
        )

        entry(props.state, 3, "ORM out", "kotlin",
                """
                    fun SQLResult.toUser() = User(
                        firstName = this.getString("firstName"),
                        lastName = this.getString("lastName"),
                        address = db.getAddress(
                                this.getString("addressId"))
                    )
                """.trimIndent()
        )

        entry(props.state, 4, "Put", "kotlin",
                """
                    fun SqlDb.putUser(u: User) = execStatement(
                        ""${"\""}INSERT INTO Users
                        (id, firstName, lastName, addressId)
                        VALUES (?, ?, ?, ?)""${"\""},
                        u.id, u.firstName, u.lastName, u.address.id
                    )
                """.trimIndent()
        )

        entry(props.state, 5, "Query", "kotlin",
                """
                    fun SqlDb.getUserByLastName(lastName: String) =
                        execQuery(
                            "GET * FROM Users WHERE lastName = ?"
                        )
                        .map { it.toUser() }
                        .toList()
                """.trimIndent()
        )

        li {
            styledSpan {
                css {
                    specific {
                        if (props.state != 6) opacity = 0.0
                    }
                }
                +"..."
            }
        }


    }
}

fun PresentationBuilder.SQLite() = slide(infos) { child(SQLiteSlide, it) }
