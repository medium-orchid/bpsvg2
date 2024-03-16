import datatypes.Mat2D
import datatypes.Vec2

typealias ElementOperation = SVGElement.() -> Unit

open class SVGElement(val tag: String, var root: SVG? = null) {

    class NameSVGElement(private val parent: SVGElement, private val tag: String) {
        operator fun invoke(vararg attributes: Pair<String, Any>, operation: ElementOperation? = null): SVGElement {
            val element = SVGElement(tag)(*attributes, operation = operation)
            parent.addChild(element)
            return element
        }
    }

    val a: NameSVGElement get() = NameSVGElement(this, "a")
    val animate: NameSVGElement get() = NameSVGElement(this, "animate")
    val animateMotion: NameSVGElement get() = NameSVGElement(this, "animateMotion")
    val animateTransform: NameSVGElement get() = NameSVGElement(this, "animateTransform")
    val circle: NameSVGElement get() = NameSVGElement(this, "circle")
    val clipPath: NameSVGElement get() = NameSVGElement(this, "clipPath")
    val desc: NameSVGElement get() = NameSVGElement(this, "desc")
    val ellipse: NameSVGElement get() = NameSVGElement(this, "ellipse")
    val feBlend: NameSVGElement get() = NameSVGElement(this, "feBlend")
    val feColorMatrix: NameSVGElement get() = NameSVGElement(this, "feColorMatrix")
    val feComponentTransfer: NameSVGElement get() = NameSVGElement(this, "feComponentTransfer")
    val feComposite: NameSVGElement get() = NameSVGElement(this, "feComposite")
    val feConvolveMatrix: NameSVGElement get() = NameSVGElement(this, "feConvolveMatrix")
    val feDiffuseLighting: NameSVGElement get() = NameSVGElement(this, "feDiffuseLighting")
    val feDisplacementMap: NameSVGElement get() = NameSVGElement(this, "feDisplacementMap")
    val feDistantLight: NameSVGElement get() = NameSVGElement(this, "feDistantLight")
    val feDropShadow: NameSVGElement get() = NameSVGElement(this, "feDropShadow")
    val feFlood: NameSVGElement get() = NameSVGElement(this, "feFlood")
    val feFuncA: NameSVGElement get() = NameSVGElement(this, "feFuncA")
    val feFuncB: NameSVGElement get() = NameSVGElement(this, "feFuncB")
    val feFuncG: NameSVGElement get() = NameSVGElement(this, "feFuncG")
    val feFuncR: NameSVGElement get() = NameSVGElement(this, "feFuncR")
    val feGaussianBlur: NameSVGElement get() = NameSVGElement(this, "feGaussianBlur")
    val feImage: NameSVGElement get() = NameSVGElement(this, "feImage")
    val feMerge: NameSVGElement get() = NameSVGElement(this, "feMerge")
    val feMergeNode: NameSVGElement get() = NameSVGElement(this, "feMergeNode")
    val feOffset: NameSVGElement get() = NameSVGElement(this, "feOffset")
    val fePointLight: NameSVGElement get() = NameSVGElement(this, "fePointLight")
    val feSpecularLighting: NameSVGElement get() = NameSVGElement(this, "feSpecularLighting")
    val feSpotLight: NameSVGElement get() = NameSVGElement(this, "feSpotLight")
    val feTile: NameSVGElement get() = NameSVGElement(this, "feTile")
    val feTurbulence: NameSVGElement get() = NameSVGElement(this, "feTurbulence")
    val filter: NameSVGElement get() = NameSVGElement(this, "filter")
    val foreignObject: NameSVGElement get() = NameSVGElement(this, "foreignObject")
    val g: NameSVGElement get() = NameSVGElement(this, "g")
    val image: NameSVGElement get() = NameSVGElement(this, "image")
    val line: NameSVGElement get() = NameSVGElement(this, "line")
    val linearGradient: NameSVGElement get() = NameSVGElement(this, "linearGradient")
    val marker: NameSVGElement get() = NameSVGElement(this, "marker")
    val mask: NameSVGElement get() = NameSVGElement(this, "mask")
    val metadata: NameSVGElement get() = NameSVGElement(this, "metadata")
    val mpath: NameSVGElement get() = NameSVGElement(this, "mpath")
    val path: PathElement.Name get() = PathElement.Name(this)
    val pattern: NameSVGElement get() = NameSVGElement(this, "pattern")
    val polygon: NameSVGElement get() = NameSVGElement(this, "polygon")
    val polyline: NameSVGElement get() = NameSVGElement(this, "polyline")
    val radialGradient: NameSVGElement get() = NameSVGElement(this, "radialGradient")
    val rect: NameSVGElement get() = NameSVGElement(this, "rect")
    val script: NameSVGElement get() = NameSVGElement(this, "script")
    val set: NameSVGElement get() = NameSVGElement(this, "set")
    val stop: NameSVGElement get() = NameSVGElement(this, "stop")
    val style: StyleElement.Name get() = StyleElement.Name(this)
    val svg: NameSVGElement get() = NameSVGElement(this, "svg")
    val switch: NameSVGElement get() = NameSVGElement(this, "switch")
    val symbol: NameSVGElement get() = NameSVGElement(this, "symbol")
    val text: NameSVGElement get() = NameSVGElement(this, "text")
    val textPath: NameSVGElement get() = NameSVGElement(this, "textPath")
    val title: NameSVGElement get() = NameSVGElement(this, "title")
    val tspan: NameSVGElement get() = NameSVGElement(this, "tspan")
    val use: NameSVGElement get() = NameSVGElement(this, "use")
    val view: NameSVGElement get() = NameSVGElement(this, "view")

    private val attributes = arrayListOf<Pair<String, Any>>()
    private val children = arrayListOf<SVGElement>()

    fun get(name: String): String {
        return "var(--$name)"
    }

    fun attributes(): Iterator<Pair<String, Any>> {
        return attributes.iterator()
    }

    fun children(): Iterator<SVGElement> {
        return children.iterator()
    }

    operator fun invoke(vararg attributes: Pair<String, Any>, operation: ElementOperation? = null): SVGElement {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) {
            this.operation()
        }
        return this
    }

    operator fun invoke(operation: ElementOperation): SVGElement {
        this.operation()
        return this
    }

    open fun define(name: String, element: SVGElement) {
        root?.define(name, element)
    }

    fun addByTag(tag: String, vararg attributes: Pair<String, Any>) {
        val element = SVGElement(tag)
        for (i in attributes) {
            element.addAttribute(i)
        }
        addChild(element)
    }

    fun addAttribute(attribute: Pair<String, Any>, first: Boolean = false) {
        val f = attribute.first
        val s = attribute.second
        if (s is Mat2D && s.approximatelyEquals(Mat2D.id)) return
        if (f.startsWith("*")) {
            val suffix = f.substring(1)
            if (s is Vec2) {
                addRawAttribute("${suffix}x" to s.x, first)
                addRawAttribute("${suffix}y" to s.y, first)
            }
        } else {
            addRawAttribute(f to s, first)
        }
    }

    private fun addRawAttribute(attribute: Pair<String, Any>, first: Boolean) {
        val f = attribute.first
        val s = attribute.second
        if (first) {
            attributes.add(0, f to s)
        } else {
            attributes.add(f to s)
        }
    }

    fun addChild(element: SVGElement) {
        children.add(element)
        element.root = root
    }

    fun addFirstChild(element: SVGElement) {
        children.add(0, element)
        element.root = root
    }

    fun string(content: String) {
        addChild(CDataElement(content))
    }

    fun removeChild(element: SVGElement) {
        children.remove(element)
    }

    fun href(id: String): Pair<String, String> {
        return "href" to "#$id"
    }

    fun url(id: String): String {
        return "url('#$id')"
    }

    fun buildInitial(svg: SVGBuilder) {
        svg.newline().append("<$tag")
        for (i in attributes) {
            svg.append(" ${i.first}=\"").append(i.second).append("\"")
        }
    }

    fun buildFinal(svg: SVGBuilder) {
        if (children.isEmpty()) {
            svg.append(" />")
        } else {
            svg.append(">")
            svg.indent()
            for (i in children) {
                i.build(svg)
            }
            svg.unindent()
            svg.newline().append("</$tag>")
        }
    }

    open fun build(svg: SVGBuilder) {
        buildInitial(svg)
        buildFinal(svg)
    }
}