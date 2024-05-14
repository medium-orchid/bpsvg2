package bpsvg2

import bpsvg2.eat.ElementAttributeTree
import bpsvg2.eat.OutputMode

class SVG(tag: String? = null, root: Boolean = false) : Element(OutputMode.XML, tag, root) {

    val a: SVG get() = makeChild(this, "a")
    val animate: SVG get() = makeChild(this, "animate")
    val animateMotion: SVG get() = makeChild(this, "animateMotion")
    val animateTransform: SVG get() = makeChild(this, "animateTransform")
    val circle: SVG get() = makeChild(this, "circle")
    val clipPath: SVG get() = makeChild(this, "clipPath")
    val defs: SVG get() = makeChild(this, "defs")
    val desc: SVG get() = makeChild(this, "desc")
    val ellipse: SVG get() = makeChild(this, "ellipse")
    val feBlend: SVG get() = makeChild(this, "feBlend")
    val feColorMatrix: SVG get() = makeChild(this, "feColorMatrix")
    val feComponentTransfer: SVG get() = makeChild(this, "feComponentTransfer")
    val feComposite: SVG get() = makeChild(this, "feComposite")
    val feConvolveMatrix: SVG get() = makeChild(this, "feConvolveMatrix")
    val feDiffuseLighting: SVG get() = makeChild(this, "feDiffuseLighting")
    val feDisplacementMap: SVG get() = makeChild(this, "feDisplacementMap")
    val feDistantLight: SVG get() = makeChild(this, "feDistantLight")
    val feDropShadow: SVG get() = makeChild(this, "feDropShadow")
    val feFlood: SVG get() = makeChild(this, "feFlood")
    val feFuncA: SVG get() = makeChild(this, "feFuncA")
    val feFuncB: SVG get() = makeChild(this, "feFuncB")
    val feFuncG: SVG get() = makeChild(this, "feFuncG")
    val feFuncR: SVG get() = makeChild(this, "feFuncR")
    val feGaussianBlur: SVG get() = makeChild(this, "feGaussianBlur")
    val feImage: SVG get() = makeChild(this, "feImage")
    val feMerge: SVG get() = makeChild(this, "feMerge")
    val feMergeNode: SVG get() = makeChild(this, "feMergeNode")
    val feOffset: SVG get() = makeChild(this, "feOffset")
    val fePointLight: SVG get() = makeChild(this, "fePointLight")
    val feSpecularLighting: SVG get() = makeChild(this, "feSpecularLighting")
    val feSpotLight: SVG get() = makeChild(this, "feSpotLight")
    val feTile: SVG get() = makeChild(this, "feTile")
    val feTurbulence: SVG get() = makeChild(this, "feTurbulence")
    val filter: SVG get() = makeChild(this, "filter")
    val foreignObject: SVG get() = makeChild(this, "foreignObject")
    val g: SVG get() = makeChild(this, "g")
    val image: SVG get() = makeChild(this, "image")
    val line: SVG get() = makeChild(this, "line")
    val linearGradient: SVG get() = makeChild(this, "linearGradient")
    val marker: SVG get() = makeChild(this, "marker")
    val mask: SVG get() = makeChild(this, "mask")
    val metadata: SVG get() = makeChild(this, "metadata")
    val mpath: SVG get() = makeChild(this, "mpath")
    val path: Path get() = Path.makeChild(this, "path")
    val pattern: SVG get() = makeChild(this, "pattern")
    val polygon: SVG get() = makeChild(this, "polygon")
    val polyline: SVG get() = makeChild(this, "polyline")
    val radialGradient: SVG get() = makeChild(this, "radialGradient")
    val rect: SVG get() = makeChild(this, "rect")
    val script: SVG get() = makeChild(this, "script")
    val set: SVG get() = makeChild(this, "set")
    val stop: SVG get() = makeChild(this, "stop")
    val style: CSS get() = CSS.makeChild(makeChild(this, "style"))
    val svg: SVG get() = makeChild(this, "svg")
    val switch: SVG get() = makeChild(this, "switch")
    val symbol: SVG get() = makeChild(this, "symbol")
    val text: SVG get() = makeChild(this, "text")
    val textPath: SVG get() = makeChild(this, "textPath")
    val title: SVG get() = makeChild(this, "title")
    val tspan: SVG get() = makeChild(this, "tspan")
    val use: SVG get() = makeChild(this, "use")
    val view: SVG get() = makeChild(this, "view")

    companion object {
        fun makeChild(parent: Element, tag: String? = null): SVG {
            val child = SVG(tag)
            parent.addChild(child)
            return child
        }

        fun root(vararg attributes: Attribute, operation: SVGOperation? = null): SVG {
            val root = SVG()
            val head = SVG("?xml")
            head.addAttributes("version" to "1.0", "encoding" to "UTF-8")
            root.addChild(head)
            val body = SVG("svg")
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

    operator fun invoke(vararg attributes: Attribute, operation: SVGOperation? = null): SVG {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) this.operation()
        return this
    }
}