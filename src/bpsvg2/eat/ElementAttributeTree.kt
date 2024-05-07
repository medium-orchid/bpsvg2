package bpsvg2.eat

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.d3.*

class ElementAttributeTree(
    val mode: OutputMode,
    val name: String? = null,
    val parent: ElementAttributeTree? = null
): DataType {
    val attributes = arrayListOf<Attribute>()
    val children = arrayListOf<ElementAttributeTree>()

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        when (mode) {
            OutputMode.XML -> putXML(builder)
            OutputMode.CSS -> putCSS(builder)
            OutputMode.Path -> putPath(builder)
            OutputMode.Text -> if (name != null) builder.append(name)
        }
    }

    fun putXML(builder: OutputBuilder) {
        if (name != null) {
            builder.newline().append("<$name")
            for (i in attributes) {
                builder.append(" ${i.first}=\"").append(i.second, mode).append("\"")
            }
            if (children.isNotEmpty()) {
                builder.append(">")
                builder.indent()
            }
        }
        for (child in children) {
            val cData = builder.cDataStrict && child.mode != OutputMode.XML
            if (cData) {
                builder.newline().append("<![CDATA[").indent()
            }
            child.put(builder, mode)
            if (cData) {
                builder.unindent().newline().append("]]>")
            }
        }
        if (name != null) {
            if (children.isEmpty()) {
                builder.append(" />").newline()
            } else {
                builder.unindent()
                builder.newline().append("</$name>")
            }
        }
    }

    fun putCSS(builder: OutputBuilder) {
        val empty = attributes.isEmpty() && children.isEmpty()
        if (name != null) {
            builder.newline().append("$name")
            if (empty) {
                builder.append(" {").indent()
            } else {
                builder.append(";")
            }
        }
        for (i in attributes) {
            builder.newline().append("${i.first}: ").append(i.second, mode).append(";")
        }
        for (child in children) {
            child.put(builder, mode)
        }
        if (name != null && !empty) {
            builder.unindent().newline().append("}")
        }
    }

    fun putPath(builder: OutputBuilder) {
        builder.newline().append("<path")
        for (i in attributes) {
            builder.append(" ${i.first}=\"").append(i.second, mode).append("\"")
        }
        builder.append(" d=\"").indent()
        for (child in children) {
            builder.newline().append(child.name ?: "")
            for (i in child.attributes) {
                builder.append(" ").append(i.second, mode)
            }
        }
        builder.unindent().newline().append("\" />")
    }

    fun valueOf(attribute: String): Any? {
        for (i in attributes) {
            if (i.first == attribute) return i.second
        }
        return null
    }

    fun childByID(id: String, deep: Boolean = true): ElementAttributeTree? {
        for (i in children) {
            if (i.valueOf("id") == id) return i
        }
        if (deep) {
            for (i in children) {
                val j = i.childByID(id, true)
                if (j != null) return j
            }
        }
        return null
    }
}