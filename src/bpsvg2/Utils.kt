package bpsvg2

typealias Attribute = Pair<String, Any>
typealias SVGOperation = SVG.() -> Unit
typealias CSSOperation = CSS.() -> Unit
typealias PathOperation = Path.() -> Unit
typealias HTMLOperation = HTML.() -> Unit

fun href(id: String): Pair<String, String> {
    return "href" to "#$id"
}

fun url(id: String): String {
    return "url('#$id')"
}

fun get(name: String): String {
    return "var(--$name)"
}

fun set(name: String): String {
    return "--$name"
}

fun id(name: String): Attribute {
    return "id" to name
}