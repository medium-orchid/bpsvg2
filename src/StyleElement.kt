typealias StyleOperation = StyleElement.() -> Unit

class StyleElement(root: SVGRoot? = null): SVGElement("style", root) {

    class Name(private val parent: SVGElement) {
        operator fun invoke(vararg attributes: Pair<String, Any>, operation: StyleOperation? = null): StyleElement {
            val element = StyleElement() (*attributes, operation = operation)
            parent.addChild(element)
            return element
        }
    }

    operator fun invoke(vararg attributes: Pair<String, Any>, operation: StyleOperation? = null): StyleElement {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) {
            this.operation()
        }
        return this
    }

    fun set(name: String): String {
        return "--$name"
    }

    fun get(name: String): String {
        return "var(--$name)"
    }

    fun select(tag: String, vararg attributes: Pair<String, Any>) {
        addByTag(tag, *attributes)
    }

    override fun build(svg: SVGBuilder) {
        buildInitial(svg)
        if (!children().hasNext()) {
            svg.append(" />")
        } else {
            svg.append(">")
            svg.indent()
            svg.newline().beginCData()
            svg.indent()
            for (i in children()) {
                svg.newline().append("${i.tag} {")
                svg.indent()
                for (j in i.attributes()) {
                    svg.newline().append("${j.first}: ").append(j.second).append(';')
                }
                svg.unindent()
                svg.newline().append("}")
            }
            svg.unindent()
            svg.newline().endCData()
            svg.unindent()
            svg.newline().append("</$tag>")
        }
    }
}