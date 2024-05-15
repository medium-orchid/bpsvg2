package bpsvg2

import bpsvg2.eat.OutputMode

class CSS(tag: String? = null, root: Boolean = false) : Element(OutputMode.CSS, tag, root) {

    companion object {
        fun makeChild(parent: Element, tag: String? = null): CSS {
            val child = CSS(tag)
            parent.addChild(child)
            return child
        }

        val root: CSS get() = CSS(null, true)
    }

    fun select(tag: String): CSS {
        return makeChild(this, tag)
    }

    fun byID(tag: String): CSS {
        return makeChild(this, "#$tag")
    }

    fun byClass(tag: String): CSS {
        return makeChild(this, ".$tag")
    }

    fun keyframes(identifier: String, operation: CSSOperation? = null): CSS {
        val element = makeChild(this, "@keyframes $identifier")
        if (operation != null) element.operation()
        return element
    }

    operator fun invoke(vararg attributes: Attribute, operation: CSSOperation? = null): CSS {
        for (i in attributes) {
            addAttributes(i)
        }
        if (operation != null) this.operation()
        return this
    }
}