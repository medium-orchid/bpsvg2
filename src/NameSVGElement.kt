class SVGElementNamer(val parent: SVGElement, val tag: String) {
    operator fun invoke(vararg attributes: Pair<String, Any>): SVGElement {
        val element = SVGElement(tag)(*attributes)
        parent.addChild(element)
        return element
    }
}