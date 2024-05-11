package bpsvg2

import bpsvg2.eat.OutputMode

class CSSElement(tag: String? = null, root: Boolean = false) : CommonElement(OutputMode.CSS, tag, root) {

    companion object {
        fun makeChild(parent: CommonElement, tag: String? = null): CSSElement {
            val child = CSSElement(tag)
            parent.addChild(child)
            return child
        }

        val root: CSSElement get() = CSSElement(null, true)
    }

    fun select(tag: String): CSSElement {
        return makeChild(this, tag)
    }

    fun keyframes(identifier: String): CSSElement {
        return makeChild(this, "@keyframes $identifier")
    }

    operator fun invoke(vararg attributes: Attribute, operation: CSSOperation? = null): CSSElement {
        for (i in attributes) {
            addAttributes(i)
        }
        if (operation != null) this.operation()
        return this
    }
}