package bpsvg2

typealias StyleOperation = StyleElement.() -> Unit

class StyleElement(root: SVG? = null) : SVGElement("style", root) {

    companion object {
        const val NO_CLASS = "``"
    }

    class Name(private val parent: SVGElement) {
        operator fun invoke(vararg attributes: Attribute, operation: StyleOperation? = null): StyleElement {
            val element = StyleElement()(*attributes, operation = operation)
            parent.addChild(element)
            return element
        }
    }

    operator fun invoke(vararg attributes: Attribute, operation: StyleOperation? = null): StyleElement {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) {
            this.operation()
        }
        return this
    }

    fun select(tag: String, vararg attributes: Attribute) {
        addByTag(tag, *attributes)
    }

    override fun build(svg: OutputBuilder) {
        if (root != null) buildInitial(svg)
        if (!children().hasNext() && root != null) {
            svg.append(" />")
        } else {
            svg.enterCSS()
            if (root != null) {
                svg.append(">")
                svg.indent()
                svg.newline().beginCData()
                svg.indent()
            }
            for (i in children()) {
                if (i.tag != NO_CLASS) {
                    svg.newline().append("${i.tag} {")
                    svg.indent()
                }
                for (j in i.attributes()) {
                    svg.newline().append("${j.first}: ").append(j.second).append(';')
                }
                if (i.tag != NO_CLASS) {
                    svg.unindent()
                    svg.newline().append("}")
                }
            }
            if (root != null) {
                svg.exitCSS()
                svg.unindent()
                svg.newline().endCData()
                svg.unindent()
                svg.newline().append("</$tag>")
            }
        }
    }
}