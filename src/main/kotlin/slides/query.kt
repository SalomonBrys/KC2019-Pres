package ws.slides

import ws.kpres.PresentationBuilder
import ws.kpres.sourceCode


fun PresentationBuilder.query() = slide {

    sourceCode(
            "kotlin",
            """
                val key = db.newKey<User>("...")
                val user = db[key]
                
                val allUsersById = db.find<User>().all()
                
                val allUsersByIndex = db.find<User>()
                        .byIndex("lastName")
                
                val allUsersWithIndex = db.find<User>()
                        .byIndex("lastName", "Doe")
                    """.trimIndent()
    ) {

    }

}
