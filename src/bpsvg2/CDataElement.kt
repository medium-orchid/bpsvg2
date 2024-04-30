package bpsvg2

class CDataElement(private val content: String, root: SVG? = null) : SVGElement("cdata", root) {
    class Name(private val parent: SVGElement) {
        operator fun invoke(content: String): CDataElement {
            val element = CDataElement(content)
            parent.addChild(element)
            return element
        }
    }

    override fun build(svg: OutputBuilder) {
        svg.newline().beginCData()
        svg.indent()
        svg.newline().append(content)
        svg.unindent()
        svg.newline().endCData()
    }
}