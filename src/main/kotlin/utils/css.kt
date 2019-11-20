package ws.utils

import kotlinext.js.js
import kotlinx.css.CSSBuilder
import kotlinx.css.RuleContainer
import kotlinx.css.TagSelector

val li get() = TagSelector("li")


//fun RuleContainer.buildObjectRules(into: dynamic) {
//    val resolvedRules = LinkedHashMap<String, CSSBuilder>()
//    rules.forEach { (selector, passStaticClassesToParent, block) ->
//        if (!resolvedRules.containsKey(selector)) {
//            resolvedRules[selector] = CSSBuilder(
//                    allowClasses = false,
//                    parent = if (passStaticClassesToParent) this@buildObjectRules else null
//            )
//        }
//        val rule = resolvedRules[selector]!!
//        rule.block()
//    }
//
//    resolvedRules.forEach {
//        into[it.key] = it.value.toObject()
//    }
//
//}
//
//fun CSSBuilder.toObject(): dynamic {
//    val dyn = js {}
//    declarations.forEach {
//        dyn[it.key] = it.value.toString()
//    }
//
//    buildObjectRules(dyn)
//
//    return dyn
//}