class NameSVGElement(val parent: SVGElement, val tag: String) {
    operator fun invoke(vararg attributes: Pair<String, Any>, operation: ElementOperation? = null): SVGElement {
        val element = SVGElement(tag)(*attributes, operation = operation)
        parent.addChild(element)
        return element
    }

    fun define(name: String, vararg attributes: Pair<String, Any>): SVGElement {
        val element = SVGElement(tag)(*attributes)
        parent.define(name, element)
        return element
    }
}