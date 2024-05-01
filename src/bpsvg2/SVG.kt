package bpsvg2

import java.io.File

class SVG(vararg attributes: Pair<String, Any>, operation: ElementOperation? = null) : SVGElement("svg") {

    private val defs = arrayListOf<SVGElement>()
    private val defined = hashSetOf<String>()

    init {
        root = this
        for (i in attributes) {
            addAttribute(i)
        }
        addAttribute("xmlns" to "http://www.w3.org/2000/svg")
        if (operation != null) {
            this.operation()
        }
    }

    override fun define(name: String, element: SVGElement) {
        if (!defined.contains(name)) {
            element.addAttribute("id" to name, true)
            removeChild(element)
            defined.add(name)
            defs.add(element)
        }
    }

    override fun toString(): String {
        val svg = OutputBuilder()
        svg.svgOpener()
        if (defs.isNotEmpty()) {
            val defsElement = SVGElement("defs", this)
            addFirstChild(defsElement)
            for (i in defs) {
                defsElement.addChild(i)
            }
        }
        build(svg)
        return svg.toString()
    }

    fun to(file: String) {
        File(file).writeText(this.toString())
    }
}