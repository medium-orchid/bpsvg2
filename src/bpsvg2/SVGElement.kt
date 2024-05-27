package bpsvg2

import bpsvg2.eat.ElementAttributeTree
import bpsvg2.eat.OutputMode

class SVGElement(tag: String? = null, root: Boolean = false) : Element(OutputMode.XML, tag, root) {

    val a: SVGElement get() = makeChild(this, "a")
    val animate: SVGElement get() = makeChild(this, "animate")
    val animateMotion: SVGElement get() = makeChild(this, "animateMotion")
    val animateTransform: SVGElement get() = makeChild(this, "animateTransform")
    val circle: SVGElement get() = makeChild(this, "circle")
    val clipPath: SVGElement get() = makeChild(this, "clipPath")
    val defs: SVGElement get() = makeChild(this, "defs")
    val desc: SVGElement get() = makeChild(this, "desc")
    val ellipse: SVGElement get() = makeChild(this, "ellipse")
    val feBlend: SVGElement get() = makeChild(this, "feBlend")
    val feColorMatrix: SVGElement get() = makeChild(this, "feColorMatrix")
    val feComponentTransfer: SVGElement get() = makeChild(this, "feComponentTransfer")
    val feComposite: SVGElement get() = makeChild(this, "feComposite")
    val feConvolveMatrix: SVGElement get() = makeChild(this, "feConvolveMatrix")
    val feDiffuseLighting: SVGElement get() = makeChild(this, "feDiffuseLighting")
    val feDisplacementMap: SVGElement get() = makeChild(this, "feDisplacementMap")
    val feDistantLight: SVGElement get() = makeChild(this, "feDistantLight")
    val feDropShadow: SVGElement get() = makeChild(this, "feDropShadow")
    val feFlood: SVGElement get() = makeChild(this, "feFlood")
    val feFuncA: SVGElement get() = makeChild(this, "feFuncA")
    val feFuncB: SVGElement get() = makeChild(this, "feFuncB")
    val feFuncG: SVGElement get() = makeChild(this, "feFuncG")
    val feFuncR: SVGElement get() = makeChild(this, "feFuncR")
    val feGaussianBlur: SVGElement get() = makeChild(this, "feGaussianBlur")
    val feImage: SVGElement get() = makeChild(this, "feImage")
    val feMerge: SVGElement get() = makeChild(this, "feMerge")
    val feMergeNode: SVGElement get() = makeChild(this, "feMergeNode")
    val feOffset: SVGElement get() = makeChild(this, "feOffset")
    val fePointLight: SVGElement get() = makeChild(this, "fePointLight")
    val feSpecularLighting: SVGElement get() = makeChild(this, "feSpecularLighting")
    val feSpotLight: SVGElement get() = makeChild(this, "feSpotLight")
    val feTile: SVGElement get() = makeChild(this, "feTile")
    val feTurbulence: SVGElement get() = makeChild(this, "feTurbulence")
    val filter: SVGElement get() = makeChild(this, "filter")
    val foreignObject: SVGElement get() = makeChild(this, "foreignObject")
    val g: SVGElement get() = makeChild(this, "g")
    val image: SVGElement get() = makeChild(this, "image")
    val line: SVGElement get() = makeChild(this, "line")
    val linearGradient: SVGElement get() = makeChild(this, "linearGradient")
    val marker: SVGElement get() = makeChild(this, "marker")
    val mask: SVGElement get() = makeChild(this, "mask")
    val metadata: SVGElement get() = makeChild(this, "metadata")
    val mpath: SVGElement get() = makeChild(this, "mpath")
    val path: Path get() = Path.makeChild(this, "path")
    val pattern: SVGElement get() = makeChild(this, "pattern")
    val polygon: SVGElement get() = makeChild(this, "polygon")
    val polyline: SVGElement get() = makeChild(this, "polyline")
    val radialGradient: SVGElement get() = makeChild(this, "radialGradient")
    val rect: SVGElement get() = makeChild(this, "rect")
    val script: SVGElement get() = makeChild(this, "script")
    val set: SVGElement get() = makeChild(this, "set")
    val stop: SVGElement get() = makeChild(this, "stop")
    val style: CSSElement get() = CSSElement.makeChild(makeChild(this, "style"))
    val svg: SVGElement get() = makeChild(this, "svg")
    val switch: SVGElement get() = makeChild(this, "switch")
    val symbol: SVGElement get() = makeChild(this, "symbol")
    val text: SVGElement get() = makeChild(this, "text")
    val textPath: SVGElement get() = makeChild(this, "textPath")
    val title: SVGElement get() = makeChild(this, "title")
    val tspan: SVGElement get() = makeChild(this, "tspan")
    val use: SVGElement get() = makeChild(this, "use")
    val view: SVGElement get() = makeChild(this, "view")

    companion object {
        fun makeChild(parent: Element, tag: String? = null): SVGElement {
            val child = SVGElement(tag)
            parent.addChild(child)
            return child
        }

        fun root(vararg attributes: Attribute, operation: SVGOperation? = null): SVGElement {
            val root = SVGElement()
            val head = SVGElement("?xml")
            head.addAttributes("version" to "1.0", "encoding" to "UTF-8")
            root.addChild(head)
            val body = SVGElement("svg")
            body.addAttributes(
                "xmlns" to "http://www.w3.org/2000/svg",
                "xmlns:xlink" to "http://www.w3.org/1999/xlink"
            )
            body.addAttributes(*attributes)
            if (operation != null) body.operation()
            root.addChild(body)
            return root
        }
    }

    fun styleAttribute(vararg attributes: Attribute): Attribute {
        val second = ElementAttributeTree(OutputMode.CSS, null, true)
        second.attributes.addAll(attributes)
        return "style" to second
    }

    operator fun invoke(vararg attributes: Attribute, operation: SVGOperation? = null): SVGElement {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) this.operation()
        return this
    }
}