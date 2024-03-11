import java.io.File

class SVGRoot(vararg attributes: Pair<String, Any>, operation: ElementOperation? = null) : SVGElement("svg") {

    private val defs = arrayListOf<SVGElement>()
    private val defined = hashSetOf<String>()

    init {
        for (i in attributes) {
            addAttribute(i)
        }
        addAttribute("xmlns" to "http://www.w3.org/2000/svg")
        if (operation != null) {
            this.operation()
        }
        root = this
    }

    override fun define(name: String, element: SVGElement) {
        if (!defined.contains(name)) {
            element.addAttribute("id" to name)
            removeChild(element)
            defined.add(name)
            defs.add(element)
        }
    }

    override fun toString(): String {
        val svg = SVGBuilder()
        if (defs.isNotEmpty()) {
            val defsElement = SVGElement("defs", this)
            for (i in defs) {
                defsElement.addChild(i)
            }
            addFirstChild(defsElement)
        }
        build(svg)
        return svg.toString()
    }

    fun to(file: String) {
        File(file).writeText(this.toString())
    }
}