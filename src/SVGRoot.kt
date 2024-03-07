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
        val str = StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        val defsElement = SVGElement("defs", this)
        for (i in defs) {
            defsElement.addChild(i)
        }
        addChild(defsElement)
        build(str)
        return str.toString()
    }
}