package bpsvg2

import bpsvg2.eat.ElementAttributeTree
import bpsvg2.eat.OutputMode

class SVGElement(tag: String? = null, parent: CommonElement? = null) :
    CommonElement(ElementAttributeTree(OutputMode.XML, tag, parent?.backingTree)) {

    companion object {
        fun root(vararg attributes: Attribute, operation: SVGOperation): SVGElement {
            val root = SVGElement()
            val head = SVGElement("?xml", root)
            head.addAttributes("version" to "1.0", "encoding" to "UTF-8")
            val body = SVGElement("svg", root)
            body.addAttributes(*attributes)
            body.operation()
            return root
        }
    }

    operator fun invoke(vararg attributes: Attribute, operation: SVGOperation) {
        for (i in attributes) {
            addAttribute(i)
        }
        this.operation()
    }
}