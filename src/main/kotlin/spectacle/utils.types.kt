//package ws.spectacle
//
//import kotlinx.css.LinearDimension
//import kotlin.reflect.KProperty
//
//object NoS {
//    class Value(private val obj: dynamic, private val key: String) {
//        object Num {
//            operator fun getValue(thisRef: Value, property: KProperty<*>) = thisRef.obj[thisRef.key] as Number?
//            operator fun setValue(thisRef: Value, property: KProperty<*>, value: Number?) { thisRef.obj[thisRef.key] = value }
//        }
//        object Str {
//            operator fun getValue(thisRef: Value, property: KProperty<*>) = thisRef.obj[thisRef.key] as String?
//            operator fun setValue(thisRef: Value, property: KProperty<*>, value: String?) { thisRef.obj[thisRef.key] = value }
//        }
//
//        var num by Num
//        var str by Str
//
//        val read get() = NoSIn(obj[key])
//    }
//    operator fun getValue(thisRef: Any, property: KProperty<*>) = Value(thisRef.asDynamic(), property.name)
//}
//
//class NoSCB(private val cb: (dynamic) -> Unit) {
//    operator fun invoke(value: Number) = cb(value)
//    operator fun invoke(value: String) = cb(value)
//}
//
//sealed class NoSIn(val dynamicValue: dynamic) {
//    data class Num(val value: Number) : NoSIn(value)
//    data class Str(val value: String) : NoSIn(value)
//    object Null : NoSIn(null)
//    companion object {
//        operator fun invoke(value: dynamic) = when (value) {
//            is Number -> Num(value)
//            is String -> Str(value)
//            null -> Null
//            else -> throw IllegalArgumentException()
//        }
//    }
//}
//
//object LD {
//    operator fun getValue(thisRef: Any, property: KProperty<*>) = (thisRef.asDynamic()[property.name] as String?)?.let { LinearDimension(it) }
//    operator fun setValue(thisRef: Any, property: KProperty<*>, value: LinearDimension?) { thisRef.asDynamic()[property.name] = value?.toString() }
//}
//
//class SE<E : Enum<E>>(private val asString: E.() -> String, private val valueOf: (String) -> E) {
//    operator fun getValue(thisRef: Any, property: KProperty<*>) = (thisRef.asDynamic()[property.name] as String?)?.let { valueOf(it) }
//    operator fun setValue(thisRef: Any, property: KProperty<*>, value: E?) { thisRef.asDynamic()[property.name] = value?.asString() }
//    companion object {
//        inline operator fun <reified E: Enum<E>> invoke() = SE<E>(Enum<E>::name, ::enumValueOf)
//    }
//}
//
//class SEArray<E : Enum<E>>(private val asString: E.() -> String, private val valueOf: (String) -> E) {
//    operator fun getValue(thisRef: Any, property: KProperty<*>) = (thisRef.asDynamic()[property.name] as Array<String>?)?.map { valueOf(it) }?.toTypedArray()
//    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Array<E>?) { thisRef.asDynamic()[property.name] = value?.map { it.asString() }?.toTypedArray() }
//    companion object {
//        inline operator fun <reified E: Enum<E>> invoke() = SEArray<E>(Enum<E>::name, ::enumValueOf)
//    }
//}
//
//interface NumberOrStringDictionary
//operator fun NumberOrStringDictionary.get(key: String) = NoS.Value(asDynamic(), key)
//operator fun NumberOrStringDictionary.set(key: String, value: Number) { asDynamic()[key] = value }
//operator fun NumberOrStringDictionary.set(key: String, value: String) { asDynamic()[key] = value }
//operator fun NumberOrStringDictionary.set(key: String, value: Nothing?) { asDynamic()[key] = value }
//
//class Serialized<T: Any, S: Any>(private val from: (T) -> S, private val to: (S) -> T) {
//    operator fun getValue(thisRef: Any, property: KProperty<*>) = (thisRef.asDynamic()[property.name] as S?)?.let(to)
//    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T?) { thisRef.asDynamic()[property.name] = value?.let(from) }
//}
