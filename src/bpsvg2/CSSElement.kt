package bpsvg2

import bpsvg2.eat.OutputMode

class CSSElement(tag: String? = null, root: Boolean = false) : Element(OutputMode.CSS, tag, root) {

    companion object {
        fun makeChild(parent: Element, tag: String? = null): CSSElement {
            val child = CSSElement(tag)
            parent.addChild(child)
            return child
        }

        val root: CSSElement get() = CSSElement(null, true)
    }

    fun select(tag: String): CSSElement {
        return makeChild(this, tag)
    }

    fun byID(tag: String): CSSElement {
        return makeChild(this, "#$tag")
    }

    fun byClass(tag: String): CSSElement {
        return makeChild(this, ".$tag")
    }

    fun keyframes(identifier: String, operation: CSSOperation? = null): CSSElement {
        val element = makeChild(this, "@keyframes $identifier")
        if (operation != null) element.operation()
        return element
    }

    operator fun invoke(vararg attributes: Attribute, operation: CSSOperation? = null): CSSElement {
        for (i in attributes) {
            addAttributes(i)
        }
        if (operation != null) this.operation()
        return this
    }
}